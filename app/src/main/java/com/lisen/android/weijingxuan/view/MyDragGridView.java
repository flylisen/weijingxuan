package com.lisen.android.weijingxuan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.lisen.android.weijingxuan.adapter.DragGridViewAdapter;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MyDragGridView extends GridView {

    private WindowManager.LayoutParams mDragViewLayoutParams;
    private int mPreDragPosition;
    private boolean mIsViewDragging;
    private ImageView mDragView;
    private WindowManager mWindowManager;
    private static final float AMP_FACTOR = 1.2f;
    private int mDownX;
    private int mDownY;
    public static final int DRAG_IMG_SHOW = 0;
    public static final int DRAG_IMG_HIDE = 1;

    public MyDragGridView(Context context) {
        this(context, null);
    }

    public MyDragGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDragGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化控件及参数
     */
    private void initView() {
        mDragViewLayoutParams = new WindowManager.LayoutParams();
        mDragView = new ImageView(getContext());
        mDragView.setTag(DRAG_IMG_HIDE);
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        setOnItemLongClickListener(onItemLongClickListener);
    }

    private OnItemLongClickListener onItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            mPreDragPosition = position;
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            mDragViewLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

            mDragViewLayoutParams.width = (int) (AMP_FACTOR * bitmap.getWidth());

            mDragViewLayoutParams.height = (int) (AMP_FACTOR * bitmap.getHeight());

            //将绘制点设置在中心
            mDragViewLayoutParams.x = (mDownX - mDragViewLayoutParams.width / 2);
            mDragViewLayoutParams.y = (mDownY - mDragViewLayoutParams.height / 2);

            mDragViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            mDragViewLayoutParams.format = PixelFormat.TRANSLUCENT;
            mDragViewLayoutParams.windowAnimations = 0;

            if ((int)mDragView.getTag() == DRAG_IMG_SHOW) {
                mWindowManager.removeView(mDragView);
                mDragView.setTag(DRAG_IMG_HIDE);
            }
            mDragView.setImageBitmap(bitmap);
            mWindowManager.addView(mDragView, mDragViewLayoutParams);
            mDragView.setTag(DRAG_IMG_SHOW);
            mIsViewDragging = true;
            ((DragGridViewAdapter)getAdapter()).hideView(position);
            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mDownX = (int) ev.getRawX();
            mDownY = (int) ev.getRawY();
        }
        else if (action == MotionEvent.ACTION_MOVE && mIsViewDragging) {
            mDragViewLayoutParams.x = (int) (ev.getRawX() - mDragView.getWidth() / 2);

            mDragViewLayoutParams.y = (int) (ev.getRawY() - mDragView.getHeight() / 2);
            mWindowManager.updateViewLayout(mDragView, mDragViewLayoutParams);
            int currDragPosition = pointToPosition((int)ev.getX(), (int)ev.getY());
            if (currDragPosition != AdapterView.INVALID_POSITION && (currDragPosition != mPreDragPosition)) {
                ((DragGridViewAdapter)getAdapter()).swap(mPreDragPosition, currDragPosition);
                mPreDragPosition = currDragPosition;
            }

        }
        else if (action == MotionEvent.ACTION_UP && mIsViewDragging) {
            ((DragGridViewAdapter)getAdapter()).showHideView();
            if ((int)mDragView.getTag() == DRAG_IMG_SHOW) {
                mWindowManager.removeView(mDragView);
                mDragView.setTag(DRAG_IMG_HIDE);
            }
            mIsViewDragging = false;
        }
        return super.onTouchEvent(ev);
    }
}

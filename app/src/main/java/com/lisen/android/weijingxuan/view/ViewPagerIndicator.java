package com.lisen.android.weijingxuan.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.lisen.android.weijingxuan.util.MyUtils;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ViewPagerIndicator extends HorizontalScrollView{

    private int mChildCount;
    private int mLastX;

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width;
        int height;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mChildCount = getChildCount();
        Log.d("TAG", "mChildCount=" + mChildCount);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = specHeight;
        } else {
            height = 100;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, specHeight);
            }
        }

       if (widthMode == MeasureSpec.AT_MOST) {
           final View childView = getChildAt(0);
           width = childView.getMeasuredWidth() * mChildCount;
           Log.d("TAG", "width=" + width);
       } else {
           width = specWidth;
       }

        if (width < MyUtils.getScreenWidth(getContext())) {
            width = MyUtils.getScreenWidth(getContext());
        }

        setMeasuredDimension(width, height);
    }*/



   /* @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            mChildWidth = childView.getMeasuredWidth();
            childView.layout(childLeft, 0, childView.getMeasuredWidth() + childLeft, childView.getMeasuredHeight());

            Log.d("TAG", "childLeft=" + childLeft);
            childLeft += childView.getMeasuredWidth();
        }
    }*/

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        boolean result = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;

                scrollBy(-deltaX, 0);

                break;
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();

                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
            }
            default:
                break;
        }

        mLastX = x;
        return true;
    }*/

   /* private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }*/

   /* @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }*/

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}

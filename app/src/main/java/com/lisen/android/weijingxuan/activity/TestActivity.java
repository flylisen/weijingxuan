package com.lisen.android.weijingxuan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.adapter.DragGridViewAdapter;
import com.lisen.android.weijingxuan.view.MyDragGridView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private List<String> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        for (int i = 0; i < 12; i++) {
            mDatas.add("item" + i);
        }
        MyDragGridView dragGridView = (MyDragGridView) findViewById(R.id.drag_view_fragment_test);
      //  DragGridViewAdapter adapter = new DragGridViewAdapter(mDatas, this);
       // dragGridView.setAdapter(adapter);
    }
}

package com.lisen.android.weijingxuan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class TestFragment extends Fragment {
    private String tag;
    private List<String> mDatas = new ArrayList<>();
     ArrayAdapter adapter;
    private Handler handler = new Handler();
    private Handler handler2 = new Handler();
    int i = 0;
    int j = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle arg = getArguments();
        tag = arg.getString("chanel_name");
        Log.d("fragment" , tag + " " + "onCreate");
        for (int i = 0; i < 12; i++) {
            mDatas.add(i + "");
        }
        super.onCreate(savedInstanceState);

    }

    private PullToRefreshListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.CENTER);
        textView.setText(tag);
        Log.d("fragment" , tag + " " + "onCreateView");
        return textView;
       /* View view = inflater.inflate(R.layout.fragment_test_layout, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.lv_test_fragment);
        adapter = new ArrayAdapter<>(getActivity(), (android.R.layout.simple_list_item_1), mDatas);
        listView.setAdapter(adapter);
        listView.setInterface(new PullToRefreshListView.IReflashListener() {
            @Override
            public void onRefresh() {
                i++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add(0, "aa" + i);
                        mDatas.add(0, "bb" + i);
                        adapter.notifyDataSetChanged();
                        listView.reflashComplete();
                    }
                }, 2000);
            }
        });

        listView.setOnLoadMoreLIstener(new PullToRefreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });*/
        //return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("fragment" , tag + " " + "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fragment" , tag + " " + "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment" , tag + " " + "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment" , tag + " " + "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fragment" , tag + " " + "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("fragment" , tag + " " + "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fragment" , tag + " " + "onDestroy");
    }



    private void loadMore() {
        j++;
        handler2.post(new Runnable() {
            @Override
            public void run() {
                mDatas.add("jj" + j);
                mDatas.add("dd" + j);
                mDatas.add("ll" + j);
                mDatas.add("kk" + j);
                adapter.notifyDataSetChanged();
            }
        });
    }
}

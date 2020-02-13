package com.zzh.sl.demo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zzh.sl.HAutoEmptyStateLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HAutoEmptyStateLayout view_state;
    private ListView lv_content;
    private final List<String> mListModel = new ArrayList<>();
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_state = findViewById(R.id.view_state);
        lv_content = findViewById(R.id.lv_content);
        lv_content.setAdapter(mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1){

        });

        // 设置自动空布局策略，监听ListView或者RecyclerView的内容自动展示空布局
        view_state.autoEmpty();
        view_state.autoEmpty();
    }

    public void onClickBtn(View view) {
        if (mListModel.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                mListModel.add(String.valueOf(i));
            }
        } else {
            mListModel.clear();
        }
        mAdapter.addAll(mListModel);
    }
}

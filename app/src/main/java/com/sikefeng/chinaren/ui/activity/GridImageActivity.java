package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.adapter.CommonAdapter;
import com.sikefeng.chinaren.ui.adapter.ViewHolder;

import java.util.Arrays;
import java.util.List;

public class GridImageActivity extends AppCompatActivity {
    private GridView gridView;
    private GridAdapter gridAdapter;
    private List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_image);
        setToolbar();
        String json = this.getIntent().getStringExtra("imageList");
        json = json.replaceAll(" ", "");
        String[] arr = json.split(",");
        imageList = Arrays.asList(arr);


        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridAdapter(this, imageList, R.layout.item_gridview_image);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GridImageActivity.this, PreviewActivity.class);
                intent.putExtra("path", gridAdapter.getItem(i));
                startActivity(intent);
            }
        });
    }

    private class GridAdapter extends CommonAdapter<String> {

        public GridAdapter(Context mContext, List<String> mDatas, int layoutId) {
            super(mContext, mDatas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, String url) {
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.draweeView);
            simpleDraweeView.setImageURI(Uri.parse(url));
        }
    }

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle(this.getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        try {
            //给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}

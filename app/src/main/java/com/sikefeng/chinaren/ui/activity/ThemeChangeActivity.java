package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.sikefeng.chinaren.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author
 * @introduction 类说明:主题更换
 */
public class ThemeChangeActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ImageView back;
    private GridView gv_gallery;
    // 存放各功能图片
    Integer[] mFunctionPics = {R.mipmap.beijing_1, R.mipmap.beijing_2, R.mipmap.beijing_3, R.mipmap.beijing_4, R.mipmap.beijing5,
            R.mipmap.beijing6, R.mipmap.beijing_7};
    // 存放各功能的名称
    String[] mFunctionName = {"魅紫星空", "城市之星", "梦幻之地", "极简主义", "璀璨星空", "简约炫纹", "岁月静守"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_theme_change);
        mContext = ThemeChangeActivity.this;
        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(this);
        gv_gallery = (GridView) findViewById(R.id.gv_gallery);

    }

    private void initData() {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int length = mFunctionName.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImageView", mFunctionPics[i]);
            map.put("ItemTextView", mFunctionName[i]);
            data.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, data, R.layout.item_gridview, new String[]{"ItemImageView", "ItemTextView"},
                new int[]{R.id.theme, R.id.theme_tv});
        gv_gallery.setAdapter(simpleAdapter);
        gv_gallery.setOnItemClickListener(new GridViewItemOnClick());
    }

    // 定义点击事件监听器
    public class GridViewItemOnClick implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
			Intent intent = new Intent();
			intent.putExtra("img", mFunctionPics[position] + "");
			setResult(1001, intent);
			finish();
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            default:
                break;
        }

    }


}

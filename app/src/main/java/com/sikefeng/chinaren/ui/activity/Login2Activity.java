package com.sikefeng.chinaren.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.SlidingDrawer;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.adapter.RecyclerGridAdapter;
import com.sikefeng.chinaren.widget.WrappingSlidingDrawer;

import java.util.ArrayList;

/**
 * 文件名：Login2Activity <br>
 * 创建时间： 2018/2/3 下午23:39 <br>
 * 文件描述：<br>
 * TODO: 这里对类的描述
 *
 * @author <a href="mailto:sikefeng.com">sikefeng</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class Login2Activity extends AppCompatActivity{

    private WrappingSlidingDrawer slidingDrawer;
    private Boolean isOpenDrawer = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        slidingDrawer= (WrappingSlidingDrawer) findViewById(R.id.slidingdrawer);
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                isOpenDrawer = true;
            }

        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

            @Override
            public void onDrawerClosed() {
                isOpenDrawer = false;
            }

        });

    }

    private void share(){
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<RecyclerGridAdapter.ShareBean> lists = new ArrayList<RecyclerGridAdapter.ShareBean>();

        RecyclerGridAdapter.ShareBean bean=new RecyclerGridAdapter.ShareBean();
        bean.setImgRes(R.mipmap.share_qq);
        bean.setShareName("QQ");
        lists.add(bean);

        RecyclerGridAdapter.ShareBean bean2=new RecyclerGridAdapter.ShareBean();
        bean2.setImgRes(R.mipmap.share_wx);
        bean2.setShareName("微信");
        lists.add(bean2);

        RecyclerGridAdapter.ShareBean bean3=new RecyclerGridAdapter.ShareBean();
        bean3.setImgRes(R.mipmap.share_wb);
        bean3.setShareName("微博");
        lists.add(bean3);

        int spanCount = 3; // 3列
        // StaggeredGridLayoutManager管理RecyclerView的布局。
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        RecyclerGridAdapter mAdapter = new RecyclerGridAdapter(lists);
        mRecyclerView.setAdapter(mAdapter);
    }


}

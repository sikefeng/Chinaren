package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityNoteListBinding;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.NoteListPresenter;
import com.sikefeng.chinaren.presenter.vm.NoteListViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NoteListActivity extends BaseActivity<ActivityNoteListBinding> {

    private NoteListPresenter presenter;
    private Context mContext;
    /**
     * 实现瀑布流效果的Manager
     */
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {

    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new NoteListPresenter(this, new NoteListViewModel(getBinding()));
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext=this;
        setToolbar(getBinding().toolbar);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().setLayoutManager(layoutManager);

        getBinding().fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, NewNoteActivity.class));
            }
        });


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_note_list;
    }


}
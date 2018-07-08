package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityNewNoteBinding;
import com.sikefeng.chinaren.entity.model.NoteBean;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.NewNotePresenter;
import com.sikefeng.chinaren.presenter.vm.NewNoteViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.ToastUtils;

public class NewNoteActivity extends BaseActivity<ActivityNewNoteBinding> {
    private Context mContext;
    private NewNotePresenter newNotePresenter;
    private NoteBean noteBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_note;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == newNotePresenter) {
            newNotePresenter = new NewNotePresenter(this, new NewNoteViewModel(getBinding()));
        }
        return newNotePresenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = this;
        noteBean = new NoteBean();
        getBinding().setNoteBean(noteBean);

        setToolbar(getBinding().toolbar);
        getBinding().toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    noteBean.setUserId(Constants.userID);
                    noteBean.setTitle(getBinding().etTitle.getText().toString());
                    noteBean.setContent(getBinding().etContent.getText().toString());
                    if ("".equals(noteBean.getTitle())) {
                        ToastUtils.showShort("请输入标题");
                    }
                    if ("".equals(noteBean.getContent())) {
                        ToastUtils.showShort("请输入内容");
                    }
                    newNotePresenter.saveNote(noteBean);
                    break;
                case R.id.action_share:

                    break;
                case R.id.action_settings:

                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

package com.sikefeng.chinaren.ui.activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityNewNoteBinding;
import com.sikefeng.chinaren.entity.model.NoteBean;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.NewNotePresenter;
import com.sikefeng.chinaren.presenter.vm.NewNoteViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.widget.VoiceEditText;
import com.sikefeng.chinaren.widget.colorpalette.ColorSelectDialog;

public class NewNoteActivity extends BaseActivity<ActivityNewNoteBinding> implements View.OnClickListener {
    private Context mContext;
    private NewNotePresenter newNotePresenter;
    private NoteBean noteBean;
    private boolean isNewRecord = false;
    private ColorSelectDialog colorSelectDialog;
    private int lastColor;
    private VoiceEditText voiceEditText;

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
        voiceEditText = (VoiceEditText) getBinding().etContent;
        noteBean = (NoteBean) getIntent().getSerializableExtra("noteBean");
        if (noteBean == null) {
            isNewRecord = true;
            noteBean = new NoteBean();
        } else {
            getBinding().etTitle.setText(noteBean.getTitle());
            getBinding().etContent.setText(noteBean.getContent());
        }
        getBinding().setNoteBean(noteBean);
        setToolbar(getBinding().toolbar);
        getBinding().toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getBinding().ivFontColor.setOnClickListener(this);
        getBinding().ivSpeech.setOnClickListener(this);


        ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //第一个参数，是描述复制的内容，也可以和内容一样。
        ClipData clipData = ClipData.newPlainText("copy from demo", "第一个参数，是描述复制的内容，也可以和内容一样");
        mClipboardManager.setPrimaryClip(clipData);

        // 粘贴板有数据，并且是文本
        if (mClipboardManager.hasPrimaryClip() && mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData.Item item = mClipboardManager.getPrimaryClip().getItemAt(0);
            CharSequence text = item.getText();
            if (text == null) {
                return;
            }
            System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkk="+text);
        }

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
                        return false;
                    }
                    if ("".equals(noteBean.getContent())) {
                        ToastUtils.showShort("请输入内容");
                        return false;
                    }
                    if (isNewRecord) {
                        newNotePresenter.saveNote(noteBean);
                    } else {
                        newNotePresenter.updateNote(noteBean);
                    }

                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivFontColor:
                if (colorSelectDialog == null) {
                    colorSelectDialog = new ColorSelectDialog(this);
                    colorSelectDialog.setOnColorSelectListener(new ColorSelectDialog.OnColorSelectListener() {
                        @Override
                        public void onSelectFinish(int selectColor) {
                            System.out.println("kkkkkkkkkkkkkkk=" + selectColor);
                            lastColor = selectColor;
                            voiceEditText.setTextColor(selectColor);
                        }
                    });
                }
                colorSelectDialog.setLastColor(lastColor);
                colorSelectDialog.show();
                break;
            case R.id.ivSpeech:
                voiceEditText.showVoiceListener();
                break;

            default:
                break;
        }
    }


}

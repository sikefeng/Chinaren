package com.sikefeng.chinaren.ui.activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityNewNoteBinding;
import com.sikefeng.chinaren.entity.model.NoteBean;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.NewNotePresenter;
import com.sikefeng.chinaren.presenter.vm.NewNoteViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.widget.PopupDialog;
import com.sikefeng.chinaren.widget.VoiceEditText;
import com.sikefeng.chinaren.widget.colorpalette.ColorSelectDialog;
import com.sikefeng.chinaren.widget.colorpalette.core.ColorPalette;

public class NewNoteActivity extends BaseActivity<ActivityNewNoteBinding> implements View.OnClickListener {
    private Context mContext;
    private NewNotePresenter newNotePresenter;
    private NoteBean noteBean;
    private boolean isNewRecord = false;
    private ColorSelectDialog colorSelectDialog;
    private int lastColor;
    private VoiceEditText voiceEditText;
    private ClipboardManager mClipboardManager;
    private PopupDialog popupDialog=null;
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
            noteBean.setBackground("");
        } else {
            getBinding().etTitle.setText(noteBean.getTitle());
            getBinding().etContent.setText(noteBean.getContent());
        }
        getBinding().setNoteBean(noteBean);
        setToolbar(getBinding().toolbar);
        getBinding().toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getBinding().ivFontColor.setOnClickListener(this);
        getBinding().ivSpeech.setOnClickListener(this);
        getBinding().ivPaste.setOnClickListener(this);
        getBinding().ivPicture.setOnClickListener(this);

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

    }
    private ColorPalette colorPalette;
    private int selectColor;
    public void showSelectColorDialog(View view){
        popupDialog= new PopupDialog(mContext, R.layout.aaa);
        RelativeLayout rel_color=popupDialog.getView(R.id.rel_color);
        TextView tv_title=popupDialog.getView(R.id.tv_title);
        TextView tv_sure=popupDialog.getView(R.id.tv_sure);
        ImageView iv_colse=popupDialog.getView(R.id.iv_colse);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  getBinding().etContent.setTextColor(selectColor);
            }
        });
        iv_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    popupDialog.dismiss();
            }
        });
        colorPalette = new ColorPalette(mContext);
        colorPalette.setLastColor(getBinding().etContent.getCurrentTextColor());
        rel_color.addView(colorPalette);
        tv_title.setTextColor(getBinding().etContent.getCurrentTextColor());
        popupDialog.setAnimation(android.R.style.Animation_InputMethod);
        popupDialog.showAtLocation(view, Gravity.TOP);
        colorPalette.setOnColorSelectListener(new ColorPalette.OnColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                selectColor=color;
                tv_title.setText(color);
            }
        });

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
                showSelectColorDialog(view);
//                if (colorSelectDialog == null) {
//                    colorSelectDialog = new ColorSelectDialog(this);
//                    colorSelectDialog.setOnColorSelectListener(new ColorSelectDialog.OnColorSelectListener() {
//                        @Override
//                        public void onSelectFinish(int selectColor) {
//                            System.out.println("kkkkkkkkkkkkkkk=" + selectColor);
//                            lastColor = selectColor;
//                            voiceEditText.setTextColor(selectColor);
//                        }
//                    });
//                }
//                colorSelectDialog.setLastColor(lastColor);
//                colorSelectDialog.show();
                break;
            case R.id.ivSpeech:
                voiceEditText.showVoiceListener();
                break;
            case R.id.ivPicture:
                startActivity(new Intent(getActivity(), ImageListActivity.class));
                break;
            case R.id.ivPaste:
                // 粘贴板有数据，并且是文本
                if (mClipboardManager.hasPrimaryClip() && mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    ClipData.Item item = mClipboardManager.getPrimaryClip().getItemAt(0);
                    CharSequence text = item.getText();
                    if (text == null) {
                        return;
                    }
                    getBinding().etContent.setText(text);
                    System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkk=" + text);
                }
                break;


            default:
                break;
        }
    }


}

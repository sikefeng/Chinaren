package com.sikefeng.chinaren.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.adapter.CommonAdapter;
import com.sikefeng.chinaren.ui.adapter.ViewHolder;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.Network;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.utils.speech.SpeechSynthesizerUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 文件名：VoicerListActivity <br>
 * 创建时间： 2018\4\24 0024 下午14:32 <br>
 * 文件描述：<br>
 * 类描述: 发音人列表界面
 *
 * @author Sikefeng <br>
 */
public class VoicerListActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private final int SELECT_VOICER_RESULT = 103;//选择发音人请求码
    private ImageView iv_return;
    private ListView listView;
    // 云端发音人列表
    private String[] cloudVoicersEntries;
    private String[] cloudVoicersValue;
    private List<String> voicersList = null;
    private HashMap<Integer, Boolean> mSelectMap = new HashMap<>();
    private VoicerAdapter voicerAdapter;
    private String voicerPath;//发音人名字拼音
    private int voicerType = 0; //0.闹钟提醒发音人  1.助理发音人

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicer_list);
        mContext = this;
        // 云端发音人名称列表
        cloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        cloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
        voicersList = java.util.Arrays.asList(cloudVoicersEntries);
        initView();
        initData();

    }


    public void initView() {
        listView = (ListView) findViewById(R.id.listView);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
    }

    public void initData() {
        voicerType = this.getIntent().getIntExtra("voicerType", 0);
        if (voicerType == 0) {
            voicerPath = this.getIntent().getStringExtra("voicerPath");
        } else if (voicerType == 1) {
            voicerPath = (String) SharePreferenceUtils.get(mContext, Constants.ROBOT_VOICERNANE, "xiaoyan");
        }
        voicerAdapter = new VoicerAdapter(mContext, voicersList, R.layout.item_list_voicer);
        if (voicerPath != null) {
            voicerAdapter.setSelectPosition(voicerPath);
        }
        listView.setAdapter(voicerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (voicerType == 0) {
                    Intent intent = VoicerListActivity.this.getIntent();
                    intent.putExtra("voicerName", cloudVoicersEntries[i]);
                    intent.putExtra("voicerPath", cloudVoicersValue[i]);
                    setResult(SELECT_VOICER_RESULT, intent);
                    finish();
                } else if (voicerType == 1) {
                    voicerPath = cloudVoicersValue[i];
                    SharePreferenceUtils.put(mContext, Constants.ROBOT_VOICERNANE, voicerPath);
                }

            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;

            default:
                break;

        }
    }


    /**
     * 发音人列表适配器
     */
    private class VoicerAdapter extends CommonAdapter<String> {

        public VoicerAdapter(Context mContext, List<String> mDatas, int layoutId) {
            super(mContext, mDatas, layoutId);
            for (int i = 0; i < voicersList.size(); i++) {
                mSelectMap.put(i, false);
            }
        }

        @Override
        public void convert(final ViewHolder holder, String name) {
            holder.setText(R.id.tv_voicer_name, name);
            CheckBox checkBox = holder.getView(R.id.checkBox);
            Button btn_select_voicer = holder.getView(R.id.btn_select_voicer);
            if (mSelectMap.get(holder.getPosition())) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
            btn_select_voicer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Network.isConnected()) {
                        ToastUtils.showShort("网络未连接");
                        return;
                    }
                    if (SpeechSynthesizerUtils.getInstance(mContext).isPlaying()) {
                        SpeechSynthesizerUtils.getInstance(mContext).stopSpeak();
                    }
                    SpeechSynthesizerUtils.getInstance(mContext).setSpeaker(cloudVoicersValue[holder.getPosition()]);//设置发音人
                    SpeechSynthesizerUtils.getInstance(mContext).startSpeak("你好我是" + cloudVoicersEntries[holder.getPosition()]);
                }
            });
        }

        /**
         * 功能描述：显示已选择的发音人
         * <br>创建时间： 2018-04-24 14:31:30
         *
         * @param title
         * @author Sikefeng
         */
        public void setSelectPosition(String title) {
            for (int i = 0; i < cloudVoicersValue.length; i++) {
                if (cloudVoicersValue[i].equals(title)) {
                    mSelectMap.put(i, true);
                } else {
                    mSelectMap.put(i, false);
                }
            }
        }
    }

}

package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityUpdateUserInfoBinding;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.UpdateUserInfoPresenter;
import com.sikefeng.chinaren.presenter.vm.UpdateUserInfoViewModel;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.chinaren.utils.StringUtil;
import com.sikefeng.chinaren.utils.ToastUtils;

import static com.sikefeng.chinaren.utils.Constants.GENDER;
import static com.sikefeng.chinaren.utils.Constants.MOTTO;
import static com.sikefeng.chinaren.utils.Constants.NICKNAME;

public class UpdateUserInfoActivity extends BaseActivity<ActivityUpdateUserInfoBinding> {

    private Context mContext;
    private UpdateUserInfoPresenter updateUserInfoPresenter;
    private String type="0"; //性别 (0.女 1.男)
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_info;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == updateUserInfoPresenter) {
            updateUserInfoPresenter = new UpdateUserInfoPresenter(this, new UpdateUserInfoViewModel(getBinding()));
        }
        return updateUserInfoPresenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = this;
        setToolbar(getBinding().toolbar);
        String nickName = (String) SharePreferenceUtils.get(mContext, NICKNAME, "");
        String motto = (String) SharePreferenceUtils.get(mContext, MOTTO, "");
        String gender = (String) SharePreferenceUtils.get(mContext, GENDER, "0");
        getBinding().tvNickname.setText(nickName);
        getBinding().tvSign.setText(motto);
        if (gender.equals("0")){
            getBinding().radioGirl.setChecked(true);
            getBinding().radioBoy.setChecked(false);
        }else {
            getBinding().radioGirl.setChecked(false);
            getBinding().radioBoy.setChecked(true);
        }
        getBinding().genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radio_girl){
                    type="0";
                }else if (checkedId==R.id.radio_boy){
                    type="1";
                }
            }
        });
        getBinding().ivSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickName = getBinding().tvNickname.getText().toString();
                String motto = getBinding().tvSign.getText().toString();
                if (StringUtil.isBlank(nickName)) {
                    ToastUtils.showShort("用户名不能为空");
                    return;
                }
                UserBean userBean = new UserBean();
                userBean.setGender(type);
                userBean.setNickName(nickName);
                userBean.setMotto(motto);
                updateUserInfoPresenter.updateMember(userBean);
            }
        });
    }
}

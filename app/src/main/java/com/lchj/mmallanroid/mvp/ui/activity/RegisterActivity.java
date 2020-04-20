package com.lchj.mmallanroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lchj.mmallanroid.di.component.DaggerRegisterComponent;
import com.lchj.mmallanroid.mvp.contract.RegisterContract;
import com.lchj.mmallanroid.mvp.model.entity.User;
import com.lchj.mmallanroid.mvp.presenter.RegisterPresenter;

import com.lchj.mmallanroid.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/18/2020 16:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegisterActivity extends MmallBaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.mUserNameEt)
    EditText mUserNameEt;
    @BindView(R.id.mMobileEt)
    EditText mMobileEt;
    @BindView(R.id.mEmailEt)
    EditText mEmailEt;
    @BindView(R.id.mPwdEt)
    EditText mPwdEt;
    @BindView(R.id.mPwdConfirmEt)
    EditText mPwdConfirmEt;
    @BindView(R.id.mQuestionEt)
    EditText mQuestionEt;
    @BindView(R.id.mAnswerEt)
    EditText mAnswerEt;
    @BindView(R.id.mRegisterBtn)
    Button mRegisterBtn;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    User user;
    private int roleStr =0;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        textChange(mUserNameEt);
        textChange(mMobileEt);
        textChange(mEmailEt);
        textChange(mPwdEt);
        textChange(mQuestionEt);
        textChange(mAnswerEt);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.mRbNoVip:
                        roleStr = 0;
                        break;
                    case R.id.mRbVip:
                        roleStr = 1;
                        break;
                }
            }
        });
    }
    private void textChange(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mRegisterBtn.setEnabled(isBtnEnable());
            }
        });
    }

    /**
     * 判断按钮是否可用
     */
    private Boolean isBtnEnable() {
        return !TextUtils.isEmpty(mUserNameEt.getText().toString().trim())
                && !TextUtils.isEmpty(mMobileEt.getText().toString().trim())
                && !TextUtils.isEmpty(mEmailEt.getText().toString().trim())
                && !TextUtils.isEmpty(mEmailEt.getText().toString().trim())
                && !TextUtils.isEmpty(mAnswerEt.getText().toString().trim())
                && !TextUtils.isEmpty(mQuestionEt.getText().toString().trim());

    }

    @OnClick(R.id.mRegisterBtn)
    void OnClick() {
        if (!mPwdEt.getText().toString().trim().equals(mPwdConfirmEt.getText().toString().trim())) {
            ArmsUtils.makeText(RegisterActivity.this, "两次密码不一致");
            return;
        }
        user = new User();
        user.setUsername(mUserNameEt.getText().toString());
        user.setPhone(mMobileEt.getText().toString());
        user.setEmail(mEmailEt.getText().toString());
        user.setPassword(mPwdEt.getText().toString());
        user.setQuestion(mQuestionEt.getText().toString());
        user.setAnswer(mAnswerEt.getText().toString());
        user.setRole(roleStr);
        mPresenter.register(user);
    }
    @Override
    public void showLoading() {
        showLoading("注册..");
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(this, message);

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void registerSuccess(String msg) {
        ArmsUtils.makeText(this, msg);
        finish();
    }

    @Override
    public void registerError(String msg) {
        ArmsUtils.makeText(this, msg);
    }
}

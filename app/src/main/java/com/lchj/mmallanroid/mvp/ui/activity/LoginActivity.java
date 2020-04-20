package com.lchj.mmallanroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lchj.mmallanroid.di.component.DaggerLoginComponent;
import com.lchj.mmallanroid.mvp.common.Const;
import com.lchj.mmallanroid.mvp.contract.LoginContract;
import com.lchj.mmallanroid.mvp.model.entity.User;
import com.lchj.mmallanroid.mvp.presenter.LoginPresenter;

import com.lchj.mmallanroid.R;
import com.lchj.mmallanroid.mvp.widgets.HeaderBar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/18/2020 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends MmallBaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.mLoginBtn)
    Button mLoginBtn;
    @BindView(R.id.mHeaderBar)
    HeaderBar mHeaderBar;

    private String userNameStr, pwdStr;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        mLoginBtn.setEnabled(isBtnEnable());
        userNameStr = SPStaticUtils.getString(Const.username);
        pwdStr = SPStaticUtils.getString(Const.pwd);
        textChange(etUser);
        textChange(etPwd);
        if (!TextUtils.isEmpty(userNameStr) && !TextUtils.isEmpty(pwdStr)) {
            etUser.setText(userNameStr);
            etPwd.setText(pwdStr);
        }
        mHeaderBar.getRightView().setVisibility(View.VISIBLE);
        mHeaderBar.getRightView().setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra(Const.username,etUser.getText().toString().trim());
            startActivity(intent);
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
                mLoginBtn.setEnabled(isBtnEnable());
            }
        });
    }

    /**
     * 判断按钮是否可用
     */
    private Boolean isBtnEnable() {
        return !TextUtils.isEmpty(etUser.getText()) && !TextUtils.isEmpty(etPwd.getText());

    }

    /**
     * 去网络登录
     */
    private void toLogin() {
        String userNameEt = etUser.getText().toString().trim();
         pwdStr = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userNameEt)) {
            ArmsUtils.makeText(this, "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwdStr)) {
            ArmsUtils.makeText(this, "密码不能为空");
            return;
        }
//        String phone = EncodeUtils.base64Encode2String(userNameEt.getBytes());//加密
//        String password = EncodeUtils.base64Encode2String(pwdEt.getBytes());//加密
        mPresenter.login(userNameEt, pwdStr);

    }

    @OnClick({R.id.mLoginBtn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLoginBtn:
                //网络登录
                toLogin();
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoading("登录..");
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
    public void loginSuccess(User user, String msg) {
        String password = EncodeUtils.base64Encode2String(pwdStr.getBytes());//加密
        //TODO：密码应该加密
        SPStaticUtils.put(Const.username, user.getUsername());
        SPStaticUtils.put(Const.pwd, pwdStr);
        ArmsUtils.makeText(this, msg);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError(String msg) {
        ArmsUtils.makeText(this, msg);
    }
}

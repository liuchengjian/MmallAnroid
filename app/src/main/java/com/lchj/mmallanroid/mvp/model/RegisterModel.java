package com.lchj.mmallanroid.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lchj.mmallanroid.mvp.contract.RegisterContract;
import com.lchj.mmallanroid.mvp.model.api.service.UserService;
import com.lchj.mmallanroid.mvp.model.entity.BaseResponse;
import com.lchj.mmallanroid.mvp.model.entity.User;

import io.reactivex.Observable;


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
@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> register(User user) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .register(user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getQuestion(),
                        user.getAnswer(),
                        user.getRole());
    }
}
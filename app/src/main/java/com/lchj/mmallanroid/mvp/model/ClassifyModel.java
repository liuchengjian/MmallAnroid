package com.lchj.mmallanroid.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lchj.mmallanroid.mvp.contract.ClassifyContract;
import com.lchj.mmallanroid.mvp.model.api.service.ClassifyService;
import com.lchj.mmallanroid.mvp.model.api.service.UserService;
import com.lchj.mmallanroid.mvp.model.entity.BaseListRes;
import com.lchj.mmallanroid.mvp.model.entity.BaseResponse;
import com.lchj.mmallanroid.mvp.model.entity.Category;
import com.lchj.mmallanroid.mvp.model.entity.Product;
import com.lchj.mmallanroid.mvp.model.entity.User;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/18/2020 22:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ClassifyModel extends BaseModel implements ClassifyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ClassifyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseResponse<List<Category>>> getOneClassify() {
        return mRepositoryManager.obtainRetrofitService(ClassifyService.class)
                .getOneClassify();
    }
    public Observable<BaseResponse<BaseListRes<Product>>> getProductList(int categoryId) {
        return mRepositoryManager.obtainRetrofitService(ClassifyService.class)
                .getProductList(categoryId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}
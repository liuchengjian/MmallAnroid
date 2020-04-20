package com.lchj.mmallanroid.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.lchj.mmallanroid.mvp.contract.ClassifyContract;
import com.lchj.mmallanroid.mvp.model.entity.BaseListRes;
import com.lchj.mmallanroid.mvp.model.entity.BaseResponse;
import com.lchj.mmallanroid.mvp.model.entity.Category;
import com.lchj.mmallanroid.mvp.model.entity.Product;
import com.lchj.mmallanroid.mvp.model.entity.User;

import java.util.List;


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
public class ClassifyPresenter extends BasePresenter<ClassifyContract.Model, ClassifyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ClassifyPresenter(ClassifyContract.Model model, ClassifyContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取一级菜单
     */
    public void getOneClassify(){
        mModel.getOneClassify()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Category>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Category>> response) {
                        if(response.isSuccess()){
                            List<Category> list= response.getData();
                            mRootView.OneClassifySuccess(list,response.getMsg());
                        }else {
                            mRootView.OneClassifyError(response.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.OneClassifyError(t.getMessage());
                    }
                });
    }
    public void getProductList(int categoryId){
        mModel.getProductList(categoryId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListRes<Product>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListRes<Product>> response) {
                        if(response.isSuccess()){
                            List<Product> list= response.getData().getList();
                            mRootView.ProductListSuccess(list,response.getMsg());
                        }else {
                            mRootView.ProductListError(response.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.ProductListError(t.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}

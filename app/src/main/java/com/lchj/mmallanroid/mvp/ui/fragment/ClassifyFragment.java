package com.lchj.mmallanroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.kennyc.view.MultiStateView;
import com.lchj.mmallanroid.di.component.DaggerClassifyComponent;
import com.lchj.mmallanroid.mvp.contract.ClassifyContract;
import com.lchj.mmallanroid.mvp.model.entity.Category;
import com.lchj.mmallanroid.mvp.model.entity.Product;
import com.lchj.mmallanroid.mvp.presenter.ClassifyPresenter;

import com.lchj.mmallanroid.R;
import com.lchj.mmallanroid.mvp.ui.adapter.TopCategoryAdapter;
import com.lchj.mmallanroid.mvp.ui.adapter.TwoCategoryAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements ClassifyContract.View {
    @BindView(R.id.mOneRecyclerView)
    RecyclerView mOneRecyclerView;
    @BindView(R.id.mTwoRecyclerView)
    RecyclerView mTwoRecyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    private TopCategoryAdapter topCategoryAdapter;
    private TwoCategoryAdapter twoCategoryAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    public static ClassifyFragment newInstance() {
        ClassifyFragment fragment = new ClassifyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerClassifyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.configRecyclerView(mOneRecyclerView, new LinearLayoutManager(getActivity()));
        mTwoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        topCategoryAdapter = new TopCategoryAdapter(getActivity(), categoryList);
        mOneRecyclerView.setAdapter(topCategoryAdapter);
        twoCategoryAdapter = new TwoCategoryAdapter(getActivity(), productList);
        mTwoRecyclerView.setAdapter(twoCategoryAdapter);
        mPresenter.getOneClassify();
        topCategoryAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                for (Category category : categoryList) {
                    category.setChecked(categoryList.get(i).getId() ==category.getId());
                }
                mPresenter.getProductList(categoryList.get(i).getId());
                topCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
//        multiStateView.startLayoutAnimation();
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(getActivity(), message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void OneClassifySuccess(List<Category> list, String msg) {
        categoryList.clear();
        if(!list.isEmpty()){
            list.get(0).setChecked(true);
            mPresenter.getProductList(list.get(0).getId());
        }
        categoryList.addAll(list);
        topCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void OneClassifyError(String msg) {
        ArmsUtils.makeText(getActivity(), msg);
    }

    @Override
    public void ProductListError(String msg) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
    }


    @Override
    public void ProductListSuccess(List<Product> list, String msg) {
        productList.clear();
        productList.addAll(list);
        if(productList.isEmpty()){
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }else {
            multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
        }
        twoCategoryAdapter.notifyDataSetChanged();
    }

}

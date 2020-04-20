package com.lchj.mmallanroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lchj.mmallanroid.di.component.DaggerMainComponent;
import com.lchj.mmallanroid.mvp.common.Const;
import com.lchj.mmallanroid.mvp.contract.MainContract;
import com.lchj.mmallanroid.mvp.presenter.MainPresenter;

import com.lchj.mmallanroid.R;
import com.lchj.mmallanroid.mvp.ui.fragment.ClassifyFragment;
import com.lchj.mmallanroid.mvp.ui.fragment.HomeFragment;
import com.lchj.mmallanroid.mvp.ui.fragment.MyFragment;
import com.lchj.mmallanroid.mvp.ui.fragment.ShopCartFragment;
import com.lchj.mmallanroid.mvp.utils.FragmentUtils;
import com.roughike.bottombar.BottomBar;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/18/2020 13:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends MmallBaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;
    private long exitTime = 0; ////记录第一次点击的时间
    private HomeFragment homeFragment;
    private ClassifyFragment classifyFragment;
    private ShopCartFragment shopCartFragment;
    private MyFragment myFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        init(savedInstanceState);
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.nav_header);
            mTitles.add(R.string.nav_classify);
            mTitles.add(R.string.nav_shop_cart);
            mTitles.add(R.string.nav_my);
        }
        if (mNavIds == null) {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_classify);
            mNavIds.add(R.id.tab_shop_cart);
            mNavIds.add(R.id.tab_my);
        }
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            classifyFragment = ClassifyFragment.newInstance();
            shopCartFragment = ShopCartFragment.newInstance();
            myFragment = MyFragment.newInstance();

        } else {
            mReplace = savedInstanceState.getInt(Const.ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            classifyFragment = (ClassifyFragment) FragmentUtils.findFragment(fm, ClassifyFragment.class);
            shopCartFragment = (ShopCartFragment) FragmentUtils.findFragment(fm, ShopCartFragment.class);
            myFragment = (MyFragment) FragmentUtils.findFragment(fm, MyFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.clear();
            mFragments.add(homeFragment);
            mFragments.add(classifyFragment);
            mFragments.add(shopCartFragment);
            mFragments.add(myFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_home:
                    mReplace = 0;
                    break;
                case R.id.tab_classify:
                    mReplace = 1;
                    break;
                case R.id.tab_shop_cart:
                    mReplace = 2;
                    break;
                case R.id.tab_my:
                    mReplace = 3;
                    break;
            }
            FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
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
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - exitTime) > 2000) {//比较两次按键时间差
            ArmsUtils.makeText(this, "再按一次退出程序");
            exitTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }
}

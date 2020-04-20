package com.lchj.mmallanroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lchj.mmallanroid.di.module.ShopCartModule;
import com.lchj.mmallanroid.mvp.contract.ShopCartContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lchj.mmallanroid.mvp.ui.fragment.ShopCartFragment;


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
@Component(modules = ShopCartModule.class, dependencies = AppComponent.class)
public interface ShopCartComponent {
    void inject(ShopCartFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ShopCartComponent.Builder view(ShopCartContract.View view);

        ShopCartComponent.Builder appComponent(AppComponent appComponent);

        ShopCartComponent build();
    }
}
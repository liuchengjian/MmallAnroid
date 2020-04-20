package com.lchj.mmallanroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lchj.mmallanroid.di.module.ClassifyModule;
import com.lchj.mmallanroid.mvp.contract.ClassifyContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lchj.mmallanroid.mvp.ui.fragment.ClassifyFragment;


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
@Component(modules = ClassifyModule.class, dependencies = AppComponent.class)
public interface ClassifyComponent {
    void inject(ClassifyFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassifyComponent.Builder view(ClassifyContract.View view);

        ClassifyComponent.Builder appComponent(AppComponent appComponent);

        ClassifyComponent build();
    }
}
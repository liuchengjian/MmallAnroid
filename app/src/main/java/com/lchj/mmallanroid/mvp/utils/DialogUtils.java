package com.lchj.mmallanroid.mvp.utils;

import android.app.Activity;
import android.graphics.Color;

import com.lchj.mmallanroid.R;
import com.zyao89.view.zloading.ZLoadingDialog;

import static com.zyao89.view.zloading.Z_TYPE.CIRCLE;

public class DialogUtils {

    public static ZLoadingDialog Dialog(Activity activity, String hintText){
        ZLoadingDialog dialog = new ZLoadingDialog(activity);
        dialog.setLoadingBuilder(CIRCLE)//设置类型
                .setLoadingColor(activity.getResources().getColor(R.color.common_blue))//颜色
                .setHintText(hintText)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(activity.getResources().getColor(R.color.common_blue))  // 设置字体颜色
                .setDurationTime(1) // 设置动画时间百分比 - 0.5倍
//                .setDialogBackgroundColor(activity.getResources().getColor(R.color.common_blue)) // 设置背景色，默认白色
                .show();
        return dialog;
    }
}

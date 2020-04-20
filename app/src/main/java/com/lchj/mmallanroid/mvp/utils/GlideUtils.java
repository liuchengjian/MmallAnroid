package com.lchj.mmallanroid.mvp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lchj.mmallanroid.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GlideUtils {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().into(imageView);
    }

    public static void loadImageFitCenter(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).fitCenter().into(imageView);
    }

    /**
     * 当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
     */
    public static void loadUrlImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(R.drawable.icon_back)
                .error(R.drawable.icon_back)
                .centerCrop()
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }
}

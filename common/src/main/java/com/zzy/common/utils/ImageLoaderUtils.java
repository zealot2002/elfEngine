package com.zzy.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zzy.core.utils.L;

/**
 * @author zzy
 * @date 2018/2/26
 */

public class ImageLoaderUtils {
    private static final String TAG = "ImageLoaderUtils";
    private ImageLoaderUtils() {}
    public static ImageLoaderUtils getInstance() {
        return ImageLoaderUtils.Holder.instance;
    }
    private static class Holder {
        private static final ImageLoaderUtils instance = new ImageLoaderUtils();
        private Holder() {}
    }

    public void showImg(final Context context, final Uri uri, final View view){
        GenericRequestBuilder request =  Glide.with(context)
                .load(uri)
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .dontAnimate()
                .priority(Priority.NORMAL);

        if(view instanceof ImageView){
            request.into((ImageView) view);
        }else{
            SimpleTarget target = new SimpleTarget<Drawable>() {
                @SuppressLint("NewApi")
                @Override
                public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {
                    view.setBackground(resource);
                }
            };
            request.into(target);
        }
    }
}

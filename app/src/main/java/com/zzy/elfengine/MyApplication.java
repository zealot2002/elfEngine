package com.zzy.elfengine;


import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.zzy.elf_template.ElfTemplateConstact;
import com.zzy.elf_template.ElfTemplateProxy;


/**
 * @author zzy
 * @date 2018/4/26
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageUriHelper.getInstance().init(this);
        ElfTemplateProxy.getInstance().init(new ElfTemplateConstact.Binder() {
            @Override
            public void onShowImage(Context context, Uri imageUri, ImageView imageView) {
                if(imageUri.toString().startsWith(ImageUriHelper.LOCAL_URI_PRE)){
                    imageUri = ImageUriHelper.getInstance().getUri(imageUri.toString());
                }
                ImageLoaderUtils.getInstance().showImg(context,imageUri,imageView);
            }

            @Override
            public void onShowImage(Context context, Uri imageUri, View view) {
                if(imageUri.toString().startsWith(ImageUriHelper.LOCAL_URI_PRE)){
                    imageUri = ImageUriHelper.getInstance().getUri(imageUri.toString());
                }
                ImageLoaderUtils.getInstance().showImg(context,imageUri,view);
            }
        });
    }
}

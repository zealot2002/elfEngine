package com.zzy.elf_template;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

/**
 * @author zzy
 * @date 2018/4/9
 */

public interface ElfTemplateConstact {

    interface Binder {
        void onShowImage(Context context, Uri imageUri, ImageView imageView);
        void onShowImage(Context context, Uri imageUri, View view);
        void onClickedEvent(Context context,String routeInfo,String statisInfo);
        Fragment onPageGroupGetFragmentEvent(Context context, String pageCode);
    }
}

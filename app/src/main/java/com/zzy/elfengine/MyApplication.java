package com.zzy.elfengine;


import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zzy.common.elf.ElfUriHelper;
import com.zzy.core.utils.L;
import com.zzy.elf_template.ElfTemplateConstact;
import com.zzy.elf_template.ElfTemplateProxy;


/**
 * @author zzy
 * @date 2018/4/26
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
/*初始化设计稿尺寸*/
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

        ElfUriHelper.getInstance().init(this);
        ElfTemplateProxy.getInstance().init(new ElfTemplateConstact.Hook() {
            @Override
            public void onSetResource(Context context, Uri uri, View view) {
                ElfUriHelper.getInstance().setResource(context,uri,view);
            }

            @Override
            public void onClickedEvent(Context context, String routeInfo, String statisInfo) {
                L.e(TAG,"onClickedEvent:routeInfo:"+routeInfo+" statisInfo:"+statisInfo);
            }
        });
    }
}

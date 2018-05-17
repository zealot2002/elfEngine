package com.zzy.elfengine;


import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.zzy.commonlib.utils.FileUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.ElfProxy;
import com.zzy.core.model.Page;
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
        ImageUriHelper.getInstance().init(this);
        ElfTemplateProxy.getInstance().init(new ElfTemplateConstact.Hook() {
            @Override
            public void onShowImage(Context context, Uri imageUri, ImageView imageView) {
                if(imageUri.toString().startsWith(ImageUriHelper.LOCAL_URI_PREFIX)){
                    imageUri = ImageUriHelper.getInstance().getUri(imageUri.toString());
                }
                ImageLoaderUtils.getInstance().showImg(context,imageUri,imageView);
            }

            @Override
            public void onShowImage(Context context, Uri imageUri, View view) {
                if(imageUri.toString().startsWith(ImageUriHelper.LOCAL_URI_PREFIX)){
                    imageUri = ImageUriHelper.getInstance().getUri(imageUri.toString());
                }
                ImageLoaderUtils.getInstance().showImg(context,imageUri,view);
            }

            @Override
            public void onClickedEvent(Context context, String routeInfo, String statisInfo) {
                L.e(TAG,"onClickedEvent:routeInfo:"+routeInfo+" statisInfo:"+statisInfo);
            }

            @Override
            public Fragment onPageGroupGetFragmentEvent(Context context, String pageCode) {
                L.e(TAG,"onPageGroupGetFragmentEvent:pageCode:"+pageCode);
                return ElfProxy.getInstance().makeRefreshPage(ElfConstants.ELF_PAGE_HOME,new ElfConstact.DataProvider() {
                    @Override
                    public void onGetDataEvent(Context context, int pageNum, ElfConstact.Callback callback) {
                        try{
                            String data = FileUtils.readFileFromAssets(context,"home.json");
                            Page page = PageJsonParser.parse(data);
                            callback.onCallback(true,page);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}

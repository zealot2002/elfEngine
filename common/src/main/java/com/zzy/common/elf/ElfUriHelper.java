package com.zzy.common.elf;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.zzy.common.R;
import com.zzy.common.utils.ImageLoaderUtils;

import java.util.HashMap;

/**
 * @author zzy
 * @date 2018/5/15
 */

public class ElfUriHelper {
    public static final String HTTP_IMAGE_URI_PREFIX = "http://";
    public static final String LOCAL_IMAGE_URI_PREFIX = "local://";
    public static final String COLOR_URI_PREFIX = "color://";
    private HashMap<String,Uri> localImgUriMap;
    private HashMap<String,Integer> localColorMap;
    /*******************************************************************************************************/
    public static ElfUriHelper getInstance(){
        return ElfUriHelper.LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final ElfUriHelper ourInstance = new ElfUriHelper();
    }
    private ElfUriHelper() {}
    public void init(Context context){
        initImgMap(context);
        initColorMap(context);
    }

    private void initColorMap(Context context) {
        localColorMap = new HashMap<>();
        localColorMap.put(COLOR_URI_PREFIX +"colorPrimary", R.color.colorPrimary);
        localColorMap.put(COLOR_URI_PREFIX +"gray", R.color.gray);
    }
    private void initImgMap(Context context){
        localImgUriMap = new HashMap<>();
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"title_icon1", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                title_icon1));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"title_icon2", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                title_icon2));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"title_icon3", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                title_icon3));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"grid_icon1", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                grid_icon1));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"grid_icon2", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                grid_icon2));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"grid_icon3", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                grid_icon3));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"grid_icon4", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                grid_icon4));

        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"special_project", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                special_project));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"normal", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                normal));
        localImgUriMap.put(LOCAL_IMAGE_URI_PREFIX +"finished", Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                finished));
    }

    private int getColor(String key){
        if(localColorMap.containsKey(key)){
            return localColorMap.get(key);
        }
        return R.color.elf_white;
    }
    private Uri getImgUri(String key){
        if(localImgUriMap.containsKey(key)){
            return localImgUriMap.get(key);
        }
        return Uri.parse("");
    }

    public void setResource(Context context,Uri uri,View view){
        if(uri.toString().startsWith(ElfUriHelper.HTTP_IMAGE_URI_PREFIX)){
            ImageLoaderUtils.getInstance().showImg(context,uri,view);
        }else if(uri.toString().startsWith(ElfUriHelper.LOCAL_IMAGE_URI_PREFIX)){
            ImageLoaderUtils.getInstance().showImg(context, getImgUri(uri.toString()),view);
        }else if(uri.toString().startsWith(ElfUriHelper.COLOR_URI_PREFIX)){
            view.setBackgroundResource(getColor(uri.toString()));
        }
    }
}

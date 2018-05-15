package com.zzy.elfengine;

import android.content.Context;
import android.net.Uri;
import java.util.HashMap;

/**
 * @author zzy
 * @date 2018/5/15
 */

public class ImageUriHelper {
    public static final String LOCAL_URI_PRE = "local://";
    private HashMap<String,Uri> localUriMap;
    /*******************************************************************************************************/
    public static ImageUriHelper getInstance(){
        return ImageUriHelper.LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final ImageUriHelper ourInstance = new ImageUriHelper();
    }
    private ImageUriHelper() {}
    public void init(Context context){
        localUriMap = new HashMap<>();
        localUriMap.put(LOCAL_URI_PRE+"ic_launcher",
                Uri.parse("android.resource://" + context.getPackageName() + "/" +R.mipmap.
                                            ic_launcher));
    }

    public Uri getUri(String key){
        if(localUriMap.containsKey(key)){
            return localUriMap.get(key);
        }
        return Uri.parse("");
    }
}

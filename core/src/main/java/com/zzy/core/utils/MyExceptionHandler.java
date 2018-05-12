package com.zzy.core.utils;

import android.content.Context;

/**
 * @author zzy
 * @date 2018/3/28
 */

public class MyExceptionHandler {
    public static void handle(Context context,String tag,Exception e){
        e.printStackTrace();
        L.e(tag,e.toString());
        MyToast.show(context, "err: " + e.toString());
    }
}

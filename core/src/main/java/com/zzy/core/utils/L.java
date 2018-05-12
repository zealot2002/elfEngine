package com.zzy.core.utils;

import android.util.Log;

import com.zzy.core.constants.CommonConstants;

/**
 * @author zzy
 * @date 2018/5/12
 */

public class L {
    public static void e(String tag,String msg){
        if(CommonConstants.isDebug)
            Log.e(tag,msg);
    }
    public static void d(String tag,String msg){
        if(CommonConstants.isDebug)
            Log.d(tag,msg);
    }
}

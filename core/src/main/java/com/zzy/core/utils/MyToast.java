package com.zzy.core.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author zzy
 * @date 2018/3/9
 */

public class MyToast {
    public static void show(Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}

package com.zzy.core.utils;

import android.support.v7.widget.RecyclerView;

/**
 * @author zzy
 * @date 2018/4/2
 */

public class ViewUtils {
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}

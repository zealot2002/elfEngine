package com.zzy.core.view.render;

import android.view.ViewGroup;


/**
 * @author zzy
 * @date 2018/2/27
 */

public interface Render<T> {
    void render(ViewGroup viewGroup, T t);
}

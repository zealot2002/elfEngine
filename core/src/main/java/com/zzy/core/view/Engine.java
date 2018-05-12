package com.zzy.core.view;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zzy.core.view.render.Render;

/**
 * @author zzy
 * @date 2018/3/21
 */

public abstract class Engine implements ItemViewDelegate,Render {
    public abstract Engine newInstance(Context context, int templateId, int layoutId,Object... args);
}

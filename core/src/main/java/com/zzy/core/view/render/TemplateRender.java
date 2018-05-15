package com.zzy.core.view.render;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

/**
 * @author zzy
 * @date 2018/3/21
 */

public abstract class TemplateRender implements ItemViewDelegate,Render {
    public abstract TemplateRender newInstance(Context context, int templateId, int layoutId, Object... args);
}

package com.zzy.core.view.render.element.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zzy.core.ElfConstact;
import com.zzy.core.ElfProxy;
import com.zzy.core.model.Section;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.render.Render;

/**
 * @author zzy
 * @date 2018/2/27
 */
public class ElementRender implements Render<Section> {
    public static final String TAG = "ElementRender";
    private Context context;
    public ElementRender(Context context) {
        this.context = context;
    }

    @Override
    public void render(ViewGroup container, Section section) {
        if(section==null){
            return;
        }
        try{
            /*get view and add it into container*/
            int layoutId = ElfProxy.getInstance().getHook().getTemplateLayoutId(section.getTemplateId());
            ViewGroup element = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, container, false);
            container.addView(element);
            /*get render and render view*/
            ElfConstact.TemplateRender elementRender = ElfProxy.getInstance().getHook().getTemplateRender(context,section.getTemplateId());
            elementRender.render(element,section);
        }catch(Exception e){
            MyExceptionHandler.handle(context,TAG,e);
        }
    }
}

package com.zzy.elf_template;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.zzy.core.ElfConstact;
import com.zzy.core.model.Page;
import com.zzy.elf_template.template.engine.Engine;
import com.zzy.elf_template.template.engine.EngineHelper;

/**
 * @author zzy
 * @date 2018/3/19
 */

public class ElfTemplateProxy {
    private ElfTemplateConstact.Hook mHook;
    /*******************************************************************************************************/
    public static ElfTemplateProxy getInstance(){
        return ElfTemplateProxy.LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final ElfTemplateProxy ourInstance = new ElfTemplateProxy();
    }
    private ElfTemplateProxy() {}

    public ElfTemplateConstact.Hook getHook() {
        return mHook;
    }

    public void init(ElfTemplateConstact.Hook hook) throws RuntimeException{
        if(hook==null){
            throw new RuntimeException("You must set hook first!");
        }
        mHook = hook;
        com.zzy.core.ElfProxy.getInstance().setHook(new ElfConstact.Hook() {
            @Override
            public SparseArray<ElfConstact.TemplateRender> getTemplateRenderList(Context context, Page page) {
                try {
                    return EngineHelper.getEngineList(context,page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Engine getTemplateRender(Context context, int templateId) throws Exception{
                return EngineHelper.getEngin(context,templateId);
            }

            @Override
            public int getTemplateLayoutId(int templateId) {
                return EngineHelper.getLayoutId(templateId);
            }

            @Override
            public void onShowImage(Context context, Uri imageUri, View view) {
                mHook.onShowImage(context,imageUri,view);
            }

            @Override
            public Fragment getFragment(Context context, String pageCode) {
                return mHook.onPageGroupGetFragmentEvent(context,pageCode);
            }
        });
    }
}

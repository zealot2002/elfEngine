package com.zzy.elf_template.template.engine.common;

import android.content.Context;
import android.view.ViewGroup;

import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.inner.recycleAdapter.ViewHolder;
import com.zzy.elf_template.R;
import com.zzy.elf_template.template.WidgetHelper;
import com.zzy.elf_template.template.engine.Engine;

import java.util.List;

/**
 * @author zzy
 * @date 2018/2/11
 */

public class SimpleEngine extends Engine {
    public static final String TAG = "SimpleEngine";
    private Context context;
    private int templateId,layoutId;

/*****************************************************************************************************/
    private SimpleEngine(Context context, int templateId, int layoutId) {
        this.context = context;
        this.templateId = templateId;
        this.layoutId = layoutId;
    }

    public SimpleEngine() {}
    @Override
    public int getItemViewLayoutId() {
        return layoutId;
    }

    @Override
    public boolean isForViewType(Object obj, int position) {
        if(obj instanceof Section){
            return ((Section)obj).getTemplateId()==templateId;
        }else{
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object obj, int position) {
        ViewGroup rootView = holder.getView(R.id.elf_rootView);
        render(rootView,obj);
    }

    @Override
    public void render(ViewGroup rootView,Object obj) {
        try{
            List<Widget> widgets = null;
            if(obj instanceof Section){
                widgets = ((Section)obj).getItemList().get(0).getWidgetList();
            }
            WidgetHelper.fillWidgets(context,rootView,widgets);
        }catch(Exception e){
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    @Override
    public Engine newInstance(Context context, int templateId, int layoutId, Object... args) {
        return new SimpleEngine(context,templateId,layoutId);
    }
}

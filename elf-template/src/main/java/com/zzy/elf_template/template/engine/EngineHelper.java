package com.zzy.elf_template.template.engine;

import android.content.Context;
import android.util.SparseArray;

import com.zzy.core.ElfConstact;
import com.zzy.core.model.Page;
import com.zzy.core.model.Section;
import com.zzy.elf_template.R;
import com.zzy.elf_template.template.engine.common.BannerEngine;
import com.zzy.elf_template.template.engine.common.RecyclerViewEngine;
import com.zzy.elf_template.template.engine.common.SimpleEngine;
import com.zzy.elf_template.template.engine.special.Engine3;

import java.util.HashMap;
import java.util.List;

/**
 * @author zzy
 * @date 2018/2/26
 */

public class EngineHelper {
    public static final String TAG = "EngineHelper";
    public static final HashMap<Integer,Integer> simpleTemplateMap = new HashMap<Integer,Integer>() {{
        put(5, R.layout.elf_template5);
        put(8, R.layout.elf_template8);
        put(10, R.layout.elf_template10);
        put(13, R.layout.elf_template6_item);
        put(14, R.layout.elf_template14);/*系统维护*/
        put(15, R.layout.elf_template15);
        put(16, R.layout.elf_template11_item);
        put(17, R.layout.elf_template17);
    }};
    public static final HashMap<Integer,Integer[]> recyclerViewTemplateMap = new HashMap<Integer,Integer[]>() {{
        put(2,new Integer[]{R.layout.elf_template2, R.layout.elf_template2_item, RecyclerViewEngine.RECYCLER_HORIZONTAL});
        put(6,new Integer[]{R.layout.elf_template6, R.layout.elf_template6_item, RecyclerViewEngine.RECYCLER_VERTICAL});
        put(11,new Integer[]{R.layout.elf_template11, R.layout.elf_template11_item, RecyclerViewEngine.RECYCLER_VERTICAL});
        put(9,new Integer[]{R.layout.elf_template9, R.layout.elf_template9_item, RecyclerViewEngine.RECYCLER_GRID_3});
    }};
    public static final HashMap<Integer,Integer> bannerTemplateMap = new HashMap<Integer,Integer>() {{
        put(4, R.layout.elf_template4);
        put(7, R.layout.elf_template7);
    }};
    public static final HashMap<Integer,Object[]> specialTemplateMap = new HashMap<Integer,Object[]>() {{
        put(3, new Object[]{Engine3.class.getName(), R.layout.elf_template3});
    }};

/**************************************************************************************************************/
    public static SparseArray<ElfConstact.TemplateRender> getEngineList(Context context, Page page) throws Exception {
        SparseArray<ElfConstact.TemplateRender> templateRenderList = new SparseArray<>();
        for(Section section:(List<Section>)page.getBody().getDataList()){
            int templateId = section.getTemplateId();
            if(templateRenderList.get(templateId)==null){
                Engine engine = getEngin(context,templateId);
                templateRenderList.put(templateId, engine);
            }
        }
        return templateRenderList;
    }

    public static Engine getEngin(Context context, int templateId) throws Exception {
        if(simpleTemplateMap.containsKey(templateId)){
            return new SimpleEngine().newInstance(context,templateId,simpleTemplateMap.get(templateId));
        }else if(recyclerViewTemplateMap.containsKey(templateId)){
            Integer[] params = recyclerViewTemplateMap.get(templateId);
            return new RecyclerViewEngine().newInstance(context,templateId,params[0],params[1],params[2]);
        }else if(bannerTemplateMap.containsKey(templateId)){
            return new BannerEngine().newInstance(context,templateId,bannerTemplateMap.get(templateId));
        }else if(specialTemplateMap.containsKey(templateId)){
            Object[] params = specialTemplateMap.get(templateId);
            Class aClass = Class.forName((String) params[0]);
            Engine render = (Engine) (aClass.getConstructor().newInstance());
            return render.newInstance(context,templateId,(Integer) params[1]);
        }
        return null;
    }
    public static boolean isValidTemplateId(int templateId){
        return (simpleTemplateMap.containsKey(templateId)
                ||recyclerViewTemplateMap.containsKey(templateId)
                ||bannerTemplateMap.containsKey(templateId)
                ||specialTemplateMap.containsKey(templateId)
                );
    }

    public static int getLayoutId(int templateId) {
        if(simpleTemplateMap.containsKey(templateId)){
            return simpleTemplateMap.get(templateId);
        }
        if(recyclerViewTemplateMap.containsKey(templateId)){
            return (int) recyclerViewTemplateMap.get(templateId)[0];
        }
        if(bannerTemplateMap.containsKey(templateId)){
            return bannerTemplateMap.get(templateId);
        }
        if(specialTemplateMap.containsKey(templateId)){
            return (int) specialTemplateMap.get(templateId)[1];
        }
        return -1;
    }
}

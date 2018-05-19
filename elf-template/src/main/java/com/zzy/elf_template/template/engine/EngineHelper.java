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

import java.util.HashMap;
import java.util.List;

/**
 * @author zzy
 * @date 2018/2/26
 */

public class EngineHelper {
    public static final String TAG = "EngineHelper";
    public static final HashMap<Integer,Integer> simpleTemplateMap = new HashMap<Integer,Integer>() {{
        put(1, R.layout.elf_template1);
        put(4, R.layout.elf_template4);
    }};
    public static final HashMap<Integer,Integer[]> recyclerViewTemplateMap = new HashMap<Integer,Integer[]>() {{
        put(3,new Integer[]{R.layout.elf_template3, R.layout.elf_template3_item, RecyclerViewEngine.RECYCLER_GRID_4});
    }};
    public static final HashMap<Integer,Integer> bannerTemplateMap = new HashMap<Integer,Integer>() {{
        put(2, R.layout.elf_template2);
    }};
    public static final HashMap<Integer,Object[]> specialTemplateMap = new HashMap<Integer,Object[]>() {{
//        put(3, new Object[]{Engine3.class.getName(), R.layout.elf_template3});
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

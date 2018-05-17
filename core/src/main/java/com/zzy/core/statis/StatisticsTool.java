package com.zzy.core.statis;

import com.zzy.core.model.Page;
import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;

import java.util.List;

/**
 * @author zzy
 * @date 2018/3/10
 */

public class StatisticsTool {
    private static final String TAG = "StatisticsTool";
    public static void sighElfPage(Page page){
        sighSectionList(page.getBody().getDataList(),page.getCode());
    }
    private static void sighSectionList(List<Section> dataList, String pageCode){
        for(int i=0;i<dataList.size();++i){
            for(int j=0;j<dataList.get(i).getItemList().size();++j){
                for(Widget w:dataList.get(i).getItemList().get(j).getWidgetList()){
                    if(w.getStatisInfo().getPageCode()!=null){
                        continue;
                    }
                    w.getStatisInfo().setPageCode(pageCode);
                    w.getStatisInfo().setSectionId(i+1);
                    w.getStatisInfo().setItemId(j+1);
                    w.getStatisInfo().setWidgetId(w.getId());
                }
            }
        }
    }
}

package com.zzy.core.view.element.body;
import com.zzy.core.model.Section;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/2/9
 */

public class PageBody implements Body<Section> ,Serializable{
    private List<Section> dataList;

    public PageBody() {
        dataList = new ArrayList<>();
    }
    @Override
    public void setDataList(List<Section> dataList) {
        this.dataList = dataList;
    }
    @Override
    public List<Section> getDataList() {
        return dataList;
    }
}


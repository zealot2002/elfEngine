package com.zzy.core.view.element.body;
import com.zzy.core.model.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/2/9
 */

public class PageListBody implements Body<Page> {
    private List<Page> dataList;
    public PageListBody() {
        dataList = new ArrayList<>();
    }
    /**********************************************************************************************/
    @Override
    public void setDataList(List<Page> dataList) {
        this.dataList = dataList;
    }
    @Override
    public List<Page> getDataList() {
        return dataList;
    }
}


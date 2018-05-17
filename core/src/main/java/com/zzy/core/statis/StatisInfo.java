package com.zzy.core.statis;

import java.io.Serializable;

/**
 * @author zzy
 * @date 2018/2/11
 *
 */
public class StatisInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String pageCode;
    private int sectionId;
    private int itemId;
    private String widgetId;

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public String toString() {
        return pageCode
                +"_"+sectionId
                +"_"+itemId
                +"_"+widgetId
                ;
    }
}

package com.zzy.core.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zzy
 * @date 2018/2/11
 */

public class Section extends Elf<Section> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int templateId;
    private int marginTop;
    private String background;
    private List<Item> itemList;

/***********************************************************************************************/
    public Section() {
        itemList = new ArrayList<>();
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return getTemplateId() == section.getTemplateId() &&
                getMarginTop() == section.getMarginTop() &&
                Objects.equals(getBackground(), section.getBackground()) &&
                Objects.equals(getItemList(), section.getItemList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getMarginTop(), getBackground(), getItemList());
    }
}

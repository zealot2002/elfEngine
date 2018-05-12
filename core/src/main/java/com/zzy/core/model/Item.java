package com.zzy.core.model;

import com.zzy.core.constants.CommonConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zzy
 * @date 2018/2/27
 */

public class Item extends Elf<Item> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Widget> widgetList;
    public Item(){
        widgetList = new ArrayList<>();
    }
    public List<Widget> getWidgetList() {
        return widgetList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getWidgetList(), item.getWidgetList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWidgetList());
    }

}

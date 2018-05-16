package com.zzy.core.model;

import com.zzy.core.statis.StatisInfo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author zzy
 * @date 2018/2/11
 */


public class Widget extends Elf<Widget> implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String route;
    private String visible;

    private String text;
    private String textColor;
    private String border;//文字边框
    private String imageUri;

    private StatisInfo statisInfo;/*统计专用*/

/***********************************************************************************************/
    public Widget() {
        statisInfo = new StatisInfo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public StatisInfo getStatisInfo() {
        return statisInfo;
    }

    public void setStatisInfo(StatisInfo statisInfo) {
        this.statisInfo = statisInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Widget)) return false;
        Widget widget = (Widget) o;
        return Objects.equals(getId(), widget.getId()) &&
                Objects.equals(getRoute(), widget.getRoute()) &&
                Objects.equals(getVisible(), widget.getVisible()) &&
                Objects.equals(getText(), widget.getText()) &&
                Objects.equals(getTextColor(), widget.getTextColor()) &&
                Objects.equals(getBorder(), widget.getBorder()) &&
                Objects.equals(getImageUri(), widget.getImageUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoute(), getVisible(), getText(), getTextColor(), getImageUri());
    }

}

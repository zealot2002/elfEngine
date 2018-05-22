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
    public Widget(){
        this("","","","",
                "","","",new StatisInfo());
    }
    private Widget(final String id,final String route,final String visible,final String text,
                   final String textColor,final String border,final String imageUri,final StatisInfo statisInfo){
        this.id = id;
        this.route = route;
        this.visible = visible;
        this.text = text;
        this.textColor = textColor;
        this.border = border;
        this.imageUri = imageUri;
        this.statisInfo = statisInfo;
    }

    public String getId() {
        return id;
    }

    public String getRoute() {
        return route;
    }

    public String getVisible() {
        return visible;
    }

    public String getText() {
        return text;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBorder() {
        return border;
    }

    public String getImageUri() {
        return imageUri;
    }

    public StatisInfo getStatisInfo() {
        return statisInfo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
        return Objects.hash(getId(), getRoute(), getVisible(), getText(), getTextColor(), getBorder(), getImageUri());
    }

    public static class WidgetBuilder{
        private String id;
        private String route;
        private String visible;

        private String text;
        private String textColor;
        private String border;//文字边框
        private String imageUri;
        private StatisInfo statisInfo;/*统计专用*/

        public WidgetBuilder(final String id){
            this.id = id;
        }
        public WidgetBuilder route(final String route){
            this.route = route;
            return this;
        }
        public WidgetBuilder visible(final String visible){
            this.visible = visible;
            return this;
        }
        public WidgetBuilder text(final String text){
            this.text = text;
            return this;
        }
        public WidgetBuilder textColor(final String textColor){
            this.textColor = textColor;
            return this;
        }
        public WidgetBuilder border(final String border){
            this.border = border;
            return this;
        }
        public WidgetBuilder imageUri(final String imageUri){
            this.imageUri = imageUri;
            return this;
        }
        public WidgetBuilder statisInfo(final StatisInfo statisInfo){
            this.statisInfo = statisInfo;
            return this;
        }
        public Widget build(){
            if(statisInfo == null){
                statisInfo = new StatisInfo();
            }
            return new Widget(id,route,visible,text, textColor,border,imageUri,statisInfo);
        }
    }
}

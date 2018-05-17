package com.zzy.core.model;

import com.zzy.core.view.element.Element;
import com.zzy.core.view.element.body.Body;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author zzy
 * @date 2018/2/9
 */

public class Page extends Elf<Page> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;  //page group专用，用来显示导航text，别无它用
    private String code;  //page的编号，也是page data的请求参数，统计也用它区分page
    private String background;
    private String type;
    private Body body;    //just a list
    private Section title,header,footer;

    public Page() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Section getTitle() {
        return title;
    }

    public void setTitle(Section title) {
        this.title = title;
    }

    public Section getHeader() {
        return header;
    }

    public void setHeader(Section header) {
        this.header = header;
    }

    public Section getFooter() {
        return footer;
    }

    public void setFooter(Section footer) {
        this.footer = footer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        Page page = (Page) o;

        return Objects.equals(getName(), page.getName()) &&
                Objects.equals(getCode(), page.getCode()) &&
                Objects.equals(getBackground(), page.getBackground()) &&
                getType() == page.getType() &&
                Objects.equals(getBody().getDataList(), page.getBody().getDataList()) &&
                Objects.equals(getTitle(), page.getTitle()) &&
                Objects.equals(getHeader(), page.getHeader()) &&
                Objects.equals(getFooter(), page.getFooter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCode(), getBackground(), getType(), getBody().getDataList(),getTitle(), getHeader(), getFooter());
    }
}


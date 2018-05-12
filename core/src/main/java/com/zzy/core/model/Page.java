package com.zzy.core.model;

import com.zzy.core.constants.CommonConstants;
import com.zzy.core.view.element.body.Body;
import com.zzy.core.view.element.footer.Footer;
import com.zzy.core.view.element.header.Header;

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
    private int type;
    private Body body;    //just a list
    private Header header;// TODO: 2018/3/17
    private Footer footer;// TODO: 2018/3/17

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
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
                Objects.equals(getHeader(), page.getHeader()) &&
                Objects.equals(getFooter(), page.getFooter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCode(), getBackground(), getType(), getBody().getDataList(), getHeader(), getFooter());
    }
}

package com.zzy.common.elf;


import com.zzy.core.ElfConstact;
import com.zzy.core.model.Item;
import com.zzy.core.model.Page;
import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.element.body.Body;
import com.zzy.core.view.element.body.PageBody;
import com.zzy.core.view.element.body.PageListBody;
import com.zzy.elf_template.template.engine.EngineHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/5/12
 */

public class ElfJsonParser {
    public static Page parse(String json) throws Exception{
        JSONTokener jsonParser = new JSONTokener(json);
        JSONObject obj = (JSONObject) jsonParser.nextValue();

        Page page = new Page();
        if (!obj.has("pageCode")) {
            throw new Exception("page must have a pageCode!");
        }
        page.setCode(obj.getString("pageCode"));
        if (!obj.has("pageType")) {
            throw new Exception("page must have a pageType!");
        }
        String pageType = obj.getString("pageType");
        page.setType(pageType);
        //fill body
        Body body;
        if(pageType.equals(ElfConstact.PAGE_TYPE_SINGLE_PAGE)){
            body = new PageBody();
            List<Section> sectionList = parseSectionList(obj);
            body.setDataList(sectionList);
        }else if(pageType.equals(ElfConstact.PAGE_TYPE_PAGE_GROUP)){
            body = new PageListBody();
            JSONArray pageArray = obj.getJSONArray("pageList");
            for (int i = 0; i < pageArray.length(); i++) {
                JSONObject pageObj = pageArray.getJSONObject(i);
                Page p = new Page();
                p.setName(pageObj.getString("pageName"));
                p.setCode(pageObj.getString("pageCode"));
                body.getDataList().add(p);
            }
        }else{
            throw new JSONException("unknown pageType!");
        }
        page.setBody(body);
        //fill others
        if (obj.has("background")) {
            page.setBackground(obj.getString("background"));
        }
        if (obj.has("title")) {
            JSONObject titleObj = obj.getJSONObject("title");
            if (titleObj.has("templateId")) {
                Section title = parseSection(titleObj);
                page.setTitle(title);
            }
        }
        if (obj.has("footer")) {
            JSONObject footerObj = obj.getJSONObject("footer");
            if (footerObj.has("templateId")) {
                Section footer = parseSection(footerObj);
                page.setFooter(footer);
            }
        }
        return page;
    }
    private static List<Section> parseSectionList(JSONObject dataObj) throws JSONException {
        List<Section> sectionList = new ArrayList<>();
        JSONArray sectionArray = dataObj.getJSONArray("sectionList");
        for (int i = 0; i < sectionArray.length(); i++) {
            JSONObject sectionObj = sectionArray.getJSONObject(i);
            Section section = parseSection(sectionObj);
            if(section!=null){
                sectionList.add(section);
            }
        }
        return sectionList;
    }
    private static Section parseSection(JSONObject sectionObj) throws JSONException{
        Section section = new Section();
        if (!sectionObj.has("templateId")) {
            throw new JSONException("A section must have a templateId!");
        }
        /*如果templateId客户端不支持，*/
        int templateId = sectionObj.getInt("templateId");
        if(!EngineHelper.isValidTemplateId(templateId)){
            return null;
        }
        section.setTemplateId(templateId);

        if (sectionObj.has("background")) {
            section.setBackground(sectionObj.getString("background"));
        }
        if (sectionObj.has("marginTop")) {
            section.setMarginTop(sectionObj.getInt("marginTop"));
        }
        /*itemList*/
        List<Item> itemList = parseItemList(sectionObj);
        section.setItemList(itemList);
        return section;
    }
    private static List<Item> parseItemList(JSONObject obj) throws JSONException {
        List<Item> itemList = new ArrayList<>();
        /*itemList*/
        JSONArray contentArray = obj.getJSONArray("itemList");
        for (int j = 0; j < contentArray.length(); j++) {
            JSONObject contentObj = contentArray.getJSONObject(j);
            Item item = new Item();
            /*widgetList*/
            JSONArray widgetArray = contentObj.getJSONArray("widgetList");
            for (int m = 0; m < widgetArray.length(); m++) {
                JSONObject widgetObj = widgetArray.getJSONObject(m);
                Widget.WidgetBuilder builder = new Widget.WidgetBuilder(widgetObj.getString("id"));

                builder.visible(widgetObj.getString("visible"));
                if (widgetObj.has("route")) {
                    builder.route(widgetObj.getString("route"));
                }
                if (widgetObj.has("text")) {
                    builder.text(widgetObj.getString("text"));
                }
                if (widgetObj.has("textColor")) {
                    builder.textColor(widgetObj.getString("textColor"));
                }
                if (widgetObj.has("border")) {
                    builder.border(widgetObj.getString("border"));
                }
                if (widgetObj.has("image")) {
                    builder.imageUri(widgetObj.getString("image"));
                }
                item.getWidgetList().add(builder.build());
            }
            itemList.add(item);
        }
        return itemList;
    }
}

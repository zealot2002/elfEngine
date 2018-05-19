package com.zzy.home.pageAdapter;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.core.ElfConstact;
import com.zzy.core.model.Item;
import com.zzy.core.model.Page;
import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.L;
import com.zzy.core.view.element.body.Body;
import com.zzy.core.view.element.body.PageBody;
import com.zzy.home.model.ProjectBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_GET;

/**
 * @author zzy
 * @date 2018/5/17
 */

public class ServiceAdapter implements ElfConstact.PageAdapter{
    private static final String TAG = "ServiceAdapter";
    @Override
    public void getPageData(Context context, int pageNum,final ElfConstact.Callback callback) {
        if(pageNum>2){
            return;
        }
        String url = HttpConstants.SERVER_URL + "/"+ HttpConstants.PROJECT_LIST;
        HInterface.JsonParser getProjectListJsonParser = new HInterface.JsonParser() {
            @Override
            public Object[] parse(String s) throws JSONException {
                return parseData(s);
            }
        };
        HInterface.DataCallback getProjectListCallback = new HInterface.DataCallback() {
            @Override
            public void requestCallback(int result, Object o, Object o1) {
                if(result == HConstant.SUCCESS){
                    Page page = makePage(o);
                    callback.onCallback(true,page);
                }else{
                    callback.onCallback(false,o.toString());
                }
            }
        };

        RequestCtx ctx = new RequestCtx.Builder(url)
                .method(HTTP_METHOD_GET)
                .callback(getProjectListCallback)
                .jsonParser(getProjectListJsonParser)
                .timerout(30)
                .build();
        try {
            L.e(TAG,"请求服务 url:"+ctx.getUrl());
            HProxy.getInstance().request(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 将业务数据转化为elf page
     * @param o
     * @return
     */
    private Page makePage(Object o) {
        Page page = new Page();
        /*设置页面类型：单页*/
        page.setType(ElfConstact.PAGE_TYPE_SINGLE_PAGE);
        /*设置页面code*/
        page.setCode("service");

        Body body = new PageBody();
        List<ProjectBean> projectList = (List<ProjectBean>) o;
        for(int i=0;i<projectList.size();i++){
            /*由于我们打算使用template4 来绘制，而template4 是单一布局模板，一个section只包含一个item*/
            /*一个project对应一个item*/
            ProjectBean projectBean = projectList.get(i);
            Item item = new Item();

            /*item根用w0来显示*/
            Widget w0 = new Widget();
            w0.setId("w0");
            w0.setVisible("1");
            item.getWidgetList().add(w0);

            /*项目名称用w1来显示*/
            Widget w1 = new Widget();
            w1.setId("w1");
            w1.setText(projectBean.getName());
            w1.setVisible("1");
            item.getWidgetList().add(w1);

            /*项目类型用w2来显示*/
            Widget w2 = new Widget();
            w2.setId("w2");
            if(projectBean.getType()==1){
                w2.setImageUri("local://special_project");
                w2.setVisible("1");
            }else{
                w2.setVisible("0");
            }
            item.getWidgetList().add(w2);

            /*项目rate用w3来显示*/
            Widget w3 = new Widget();
            w3.setId("w3");
            w3.setText(projectBean.getRate());
            w3.setVisible("1");
            item.getWidgetList().add(w3);

            /*w4是一个%，打开它*/
            Widget w4 = new Widget();
            w4.setId("w4");
            w4.setText("%");
            w4.setVisible("1");
            item.getWidgetList().add(w4);

            /*项目duration用w5来显示*/
            Widget w5 = new Widget();
            w5.setId("w5");
            w5.setText(projectBean.getDuration());
            w5.setVisible("1");
            item.getWidgetList().add(w5);

            /*项目state用w6来显示*/
            Widget w6 = new Widget();
            w6.setId("w6");
            if(projectBean.getState()==1){
                w6.setImageUri("local://normal");
            }else{
                w6.setImageUri("local://finished");
            }
            w6.setVisible("1");
            item.getWidgetList().add(w6);

            /*项目desc用w7来显示*/
            Widget w7 = new Widget();
            w7.setId("w7");
            w7.setText(projectBean.getDesc());
            w7.setVisible("1");
            item.getWidgetList().add(w7);

            /*项目startMoney用w8来显示*/
            Widget w8 = new Widget();
            w8.setId("w8");
            w8.setText(projectBean.getStartMoney());
            w8.setVisible("1");
            item.getWidgetList().add(w8);

            /*项目startTime用w9来显示*/
            Widget w9 = new Widget();
            w9.setId("w9");
            w9.setText(projectBean.getStartTime());
            w9.setVisible("1");
            item.getWidgetList().add(w9);

            Section section = new Section();
            /*对于单一template，一个section只有一个item*/
            section.getItemList().add(item);
            /*设置templateId*/
            section.setTemplateId(4);
            section.setMarginTop(40);
            body.getDataList().add(section);
        }
        page.setBody(body);
        return page;
    }

    /**
     * 解析业务接口数据
     * @param str
     * @return
     * @throws JSONException
     */
    public Object[] parseData(String str) throws JSONException {
        L.e(TAG,"server return:"+str);
        JSONTokener jsonParser = new JSONTokener(str);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        int errorCode = obj.getInt("errorCode");
        if (errorCode == HttpConstants.NO_ERROR) {
            List<ProjectBean> projectList = new ArrayList<>();
            JSONObject dataObj = obj.getJSONObject("data");
            JSONArray projectArray = dataObj.getJSONArray("projectList");
            for (int i = 0; i < projectArray.length(); i++) {
                JSONObject projectObj = projectArray.getJSONObject(i);
                ProjectBean projectBean = new ProjectBean();

                projectBean.setName(projectObj.getString("name"));
                projectBean.setDesc(projectObj.getString("desc"));
                projectBean.setDuration(projectObj.getString("duration"));
                projectBean.setRate(projectObj.getString("rate"));
                projectBean.setStartMoney(projectObj.getString("startMoney"));
                projectBean.setStartTime(projectObj.getString("startTime"));

                projectBean.setState(projectObj.getInt("state"));
                projectBean.setType(projectObj.getInt("type"));

                projectList.add(projectBean);
            }
            return new Object[]{HConstant.SUCCESS, projectList};
        } else {
            String msg = obj.getString("errorMessage");
            return new Object[]{HConstant.FAIL, msg};
        }
    }











    @Override
    public Fragment getFragment(Context context, String pageCode) {
        return null;
    }
}

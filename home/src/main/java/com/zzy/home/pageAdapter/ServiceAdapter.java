package com.zzy.home.pageAdapter;

import android.content.Context;
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
    private boolean bTestNoNet = true;
    @Override
    public void getPageData(Context context, int pageNum,final ElfConstact.Callback callback) {
        //for test
        if(pageNum == 1){
            bTestNoNet = true;
        }
        if(pageNum==2&&bTestNoNet){
            bTestNoNet = false;
            callback.onCallback(false,"网络加载失败");
            return;
        }
        if(pageNum==4){
            //全部加载完毕，没有新数据了
            callback.onCallback(true,new Page());
            return;
        }
        //for test 获取业务接口数据
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
            Widget w0 = new Widget.WidgetBuilder("w0")
                    .visible("1")
                    .build();
            item.getWidgetList().add(w0);

            /*项目名称用w1来显示*/
            Widget w1 = new Widget.WidgetBuilder("w1")
                    .visible("1")
                    .text(projectBean.getName())
                    .build();
            item.getWidgetList().add(w1);

            /*项目类型用w2来显示*/
            if(projectBean.getType()==1){
                Widget w2 = new Widget.WidgetBuilder("w2")
                        .visible("1")
                        .imageUri("local://special_project")
                        .build();
                item.getWidgetList().add(w2);
            }

            /*项目rate用w3来显示*/
            Widget w3 = new Widget.WidgetBuilder("w3")
                    .visible("1")
                    .text(projectBean.getRate())
                    .build();
            item.getWidgetList().add(w3);

            /*w4是一个%*/
            Widget w4 = new Widget.WidgetBuilder("w4")
                    .visible("1")
                    .text("%")
                    .build();
            item.getWidgetList().add(w4);

            /*项目duration用w5来显示*/
            Widget w5 = new Widget.WidgetBuilder("w5")
                    .visible("1")
                    .text(projectBean.getDuration())
                    .build();
            item.getWidgetList().add(w5);

            /*项目state用w6来显示*/
            Widget.WidgetBuilder builder = new Widget.WidgetBuilder("w6");
            builder.imageUri(projectBean.getState()==1?
                    "local://normal"
                    :
                    "local://finished");
            item.getWidgetList().add(builder.build());

            /*项目desc用w7来显示*/
            Widget w7 = new Widget.WidgetBuilder("w7")
                    .visible("1")
                    .text(projectBean.getDesc())
                    .build();
            item.getWidgetList().add(w7);

            /*项目startMoney用w8来显示*/
            Widget w8 = new Widget.WidgetBuilder("w8")
                    .visible("1")
                    .text(projectBean.getStartMoney())
                    .build();
            item.getWidgetList().add(w8);

            /*项目startTime用w9来显示*/
            Widget w9 = new Widget.WidgetBuilder("w9")
                    .visible("1")
                    .text(projectBean.getStartTime())
                    .build();
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

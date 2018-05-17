package com.zzy.core;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zzy.core.model.Page;
import com.zzy.core.view.render.Render;

import java.util.List;


/**
 * @author zzy
 * @date 2018/4/9
 */

public interface ElfConstact {
    public static final String PAGE_TYPE_SINGLE_PAGE = "singlePage";
    public static final String PAGE_TYPE_PAGE_GROUP = "pageGroup";
    interface Req {
        void setDebugMode(boolean b);
        void setBinder(Binder binder);
        /**
         * 普通页面
         */
        Fragment makeNormalPage(String pageCode,DataProvider dataProvider);

        /**
         * 带有下拉刷新和上拉加载
         * @param dataProvider
         * @return
         */
        Fragment makeRefreshPage(String pageCode,DataProvider dataProvider);
        /*通知elf page刷新*/
        void notifyDataSetChanged(String pageCode);

        /*通知elf page刷新*/
        void notifyDataSetChanged(List<String> pageCodeList);

        /*通知elf pageGroup transfer page*/
        void notifyPageGroupTransferPage(String pageGroupCode,String pageCode);

    }
    interface Callback {
        void onCallback(boolean bResult, Object data);
    }
    interface DataProvider{
        void onGetDataEvent(Context context, int pageNum, Callback callback);
    }
    interface TemplateRender extends ItemViewDelegate,Render {}
    interface Binder {
        SparseArray<TemplateRender> getTemplateRenderList(Context context, Page page);
        TemplateRender getTemplateRender(Context context,int templateId) throws Exception;
        int getTemplateLayoutId(int templateId) throws Exception;
        void onShowImage(Context context, Uri imageUri, View view);
        Fragment onPageGroupGetFragmentEvent(Context context,String pageCode);
    }
}

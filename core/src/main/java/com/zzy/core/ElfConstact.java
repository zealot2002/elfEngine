package com.zzy.core;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.zzy.core.model.Page;
import com.zzy.core.view.inner.recycleAdapter.ItemViewDelegate;
import com.zzy.core.view.render.Render;

import java.util.List;


/**
 * 精灵引擎契约
 * @author zzy
 * @date 2018/4/9
 */

public interface ElfConstact {
    //elf page type enum
    /*单页*/
    String PAGE_TYPE_SINGLE_PAGE = "singlePage";
    /*页组*/
    String PAGE_TYPE_PAGE_GROUP = "pageGroup";

    /**
     * 对外接口
     */
    interface Req {
        /**
         * @param b
         */
        void setDebugMode(boolean b);

        /**
         * @param hook
         */
        void setHook(Hook hook);
        /**
         * 制作一个普通页面
         */
        Fragment makeNormalPage(String pageCode,PageAdapter adapter);
        /**
         * 制作一个带有下拉刷新和上拉加载的页面
         * @param adapter
         * @return
         */
        Fragment makeRefreshPage(String pageCode,PageAdapter adapter);
        /**
         * 通知指定pageCode的elf fragment刷新
         * @param pageCode
         */
        void notifyDataSetChanged(String pageCode);
        /**
         * 通知指定pageCode的elf fragment刷新
         * @param pageCodeList
         */
        void notifyDataSetChanged(List<String> pageCodeList);
        /**
         * 通知指定pageGroupCode的页组切换到指定pageCode的子fragment
         * @param pageGroupCode
         * @param pageCode
         */
        void notifyPageGroupTransferPage(String pageGroupCode,String pageCode);

    }

    /**
     * 通用Callback
     */
    interface Callback {
        void onCallback(boolean bResult, Object object);
    }

    /**
     * 页面数据提供者
     */
    interface PageAdapter{
        /**
         *  获取页面数据
         * @param context
         * @param pageNum  第几页，从1开始
         * @param callback
         */
        void getPageData(Context context, int pageNum, Callback callback);

        /**
         * 页组专用：获取fragment
         * @param context
         * @param pageCode
         * @return
         */
        Fragment getFragment(Context context, String pageCode);
    }

    /**
     * template(Section)的渲染器
     * app根据自身情况，定制一套templateRender，供elfEngine引用
     */
    interface TemplateRender extends ItemViewDelegate,Render {}
    /*elf hook*/
    interface Hook {
        /**
         * 获取app定制的templateRender列表
         * @param context
         * @param page
         * @return
         */
        SparseArray<TemplateRender> getTemplateRenderList(Context context, Page page);

        /**
         * 获取指定templateId的render
         * @param context
         * @param templateId
         * @return
         * @throws Exception
         */
        TemplateRender getTemplateRender(Context context,int templateId) throws Exception;

        /**
         * 获取指定templateId的layoutId
         * @param templateId
         * @return
         * @throws Exception
         */
        int getTemplateLayoutId(int templateId) throws Exception;

        /**
         * @param context
         * @param resourceUri
         * @param view
         */
        void onSetResource(Context context, Uri resourceUri, View view);

        /**
         * elf fragment全屏幕无网络布局,app定制
         */
        int getCustomDisconnectLayoutId();

        /**
         * elf list底部loading布局,app定制
         * @return
         */
        int getCustomListLoadingLayoutId();

        /**
         * elf list底部load failed布局,app定制
         * @return
         */
        int getCustomListLoadFailedLayoutId();

        /**
         * elf list底部load end布局,app定制
         * @return
         */
        int getCustomListLoadEndLayoutId();
    }
}

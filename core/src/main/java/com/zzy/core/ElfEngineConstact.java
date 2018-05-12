package com.zzy.core;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zzy.core.model.Page;
import com.zzy.core.view.Engine;

import java.util.List;


/**
 * @author zzy
 * @date 2018/4/9
 */

public interface ElfEngineConstact {

    public static final int ELF_NORMAL_PAGE = 1;
    /**
     * 联网页面
     */
    public static final int ELF_NET_PAGE = 2;
    /**
     * 带有下拉刷新
     */
    public static final int ELF_NET_PAGE_WITH_REFRESH = 3;
    /**
     * 带有下拉刷新、上拉加载
     */
    public static final int ELF_NET_PAGE_WITH_REFRESH_AND_LOAD_MORE = 4;

    interface Req {
        void setDebugMode(boolean b);
        void setBinder(Binder binder);
        /**
         * 普通页面
         */
        Fragment makeNormalElfPage(DataProvider dataProvider);
    }
    interface Callback {
        /**
         *
         * @param result  0:fail; 1:success
         * @param data
         */
        void onCallback(int result, Object data);
    }
    interface DataProvider{
        Page getPageData(Context context);
    }
    interface Binder {
        /**
         *
         * @param routeInfo  路由信息
         * @param statisInfo 统计信息
         */
        void onClickedEvent(String routeInfo,String statisInfo);

        void showImage(final Context context, final String uri, final View view);

        List<Engine> getEngineList();
    }
}

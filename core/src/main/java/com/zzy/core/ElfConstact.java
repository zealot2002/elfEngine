package com.zzy.core;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.zzy.core.model.Page;
import com.zzy.core.view.render.TemplateRender;

import java.util.List;


/**
 * @author zzy
 * @date 2018/4/9
 */

public interface ElfConstact {
    interface Req {
        void setDebugMode(boolean b);
        void setBinder(Binder binder);
        /**
         * 普通页面
         */
        Fragment makeNormalPage(NormalDataProvider dataProvider);

        /**
         * 带有下拉刷新和上拉加载
         * @param dataProvider
         * @return
         */
        Fragment makeRefreshPage(RefreshDataProvider dataProvider);
    }
    interface Callback {
        /**
         *
         * @param bResult
         * @param data
         */
        void onCallback(boolean bResult, Object data);
    }
    interface NormalDataProvider{
        void onGetDataEvent(Context context, Callback callback);
    }
    interface RefreshDataProvider{
        void onGetDataEvent(Context context, int pageNum, Callback callback);
    }
    interface Binder {
        SparseArray<TemplateRender> getTemplateRenderList(Context context, Page page);
        void onShowImage(Context context, Uri imageUri, View view);
    }
}

package com.zzy.core.constact;

import android.content.Context;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;
import com.zzy.core.model.Page;


/**
 * @author zzy
 * @date 2018/2/27
 */

public interface PageConstact {
    interface View extends BaseLoadingView {
        void updatePage(Page page, int pageNum);
    }

    interface Presenter extends BasePresenter {
        void getPageData(Context context, final String pageCode, boolean bShow, int pageNum);
    }
}

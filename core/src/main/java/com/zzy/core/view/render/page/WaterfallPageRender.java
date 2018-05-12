package com.zzy.core.view.render.page;

import java.util.List;


/**
 * @author zzy
 * @date 2018/2/27
 */

public interface WaterfallPageRender<T> extends PageRender {
    interface EventListener{
        void onReload();
        void onLoadMore(int pageNum);
    }
    void appendUpdateData(List<T> list);
    void setEventListener(EventListener eventListener);
}

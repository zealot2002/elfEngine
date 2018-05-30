package com.zzy.core.view.inner.recycleAdapter;

/**
 * @author zzy
 * @date 2018/5/23
 */

public interface ItemViewDelegate<T>{
    int getItemViewLayoutId();
    boolean isForViewType(T item, int position);
    void convert(ViewHolder holder, T t, int position);
}

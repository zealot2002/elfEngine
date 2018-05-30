package com.zzy.core.view.inner;

import android.content.Context;

import com.zzy.core.model.Section;
import com.zzy.core.view.inner.recycleAdapter.ItemViewDelegate;
import com.zzy.core.view.inner.recycleAdapter.ItemViewDelegateManager;
import com.zzy.core.view.inner.recycleAdapter.MultiBaseAdapter;
import com.zzy.core.view.inner.recycleAdapter.ViewHolder;

import java.util.List;

public class MyMultiRecycleAdapter extends MultiBaseAdapter<Section> {
    protected Context mContext;
    protected List<Section> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;
/*****************************************************************************************************/
    public MyMultiRecycleAdapter(Context context, List<Section> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    protected void convert(ViewHolder holder, final Section data, int position, int viewType) {
        mItemViewDelegateManager.convert(holder, data, holder.getAdapterPosition());
    }

    public MyMultiRecycleAdapter addItemViewDelegate(ItemViewDelegate itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return mItemViewDelegateManager.getItemViewLayoutId(viewType);
    }

    @Override
    protected int getViewType(int position, Section data) {
        return mItemViewDelegateManager.getItemViewType(data,position);
    }
}

package com.zzy.core.view.inner;

import android.support.v7.widget.RecyclerView;

import com.zzy.core.utils.L;
import com.zzy.core.utils.ViewUtils;

public abstract class WaterfallOnScrollListener extends  RecyclerView.OnScrollListener{
    private static final String TAG = "WaterfallOnScrollListener";
    private boolean loading = false;

    public WaterfallOnScrollListener() {
    }

    public void reset(){
        loading = false;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(!loading){
            if(ViewUtils.isSlideToBottom(recyclerView)){
                loading = true;
                onLoadMore();
            }
        }
    }
    public abstract void onLoadMore();
}


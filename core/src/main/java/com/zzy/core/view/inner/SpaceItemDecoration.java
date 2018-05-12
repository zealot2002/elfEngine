package com.zzy.core.view.inner;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.core.model.Section;

import java.util.List;


/**
 * Created by zhaoziying on 2017/5/3.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private List<Section> sectionList;

    public SpaceItemDecoration(Context context, List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = 0;
        outRect.left = 0;
        outRect.right = 0;
        int position = parent.getChildLayoutPosition(view);
        //去掉loadmore
        if(position < sectionList.size()){
            int marginTop = AutoUtils.getPercentHeightSize(sectionList.get(position).getMarginTop());
            outRect.top = marginTop;
        }else{
            outRect.top = 0;
        }
    }
}


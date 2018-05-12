package com.zzy.core.view.inner;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author zzy
 * @date 2018/2/12
 */

public class MyMultiAdapter extends MultiItemTypeAdapter {
    public MyMultiAdapter(Context context, List datas) {
        super(context, datas);
    }
    public void setDataList(List datas){
        mDatas = datas;
    }

}

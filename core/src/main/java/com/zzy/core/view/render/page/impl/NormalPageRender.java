package com.zzy.core.view.render.page.impl;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.core.ElfProxy;
import com.zzy.core.R;
import com.zzy.core.model.Page;
import com.zzy.core.statis.StatisticsTool;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.render.TemplateRender;
import com.zzy.core.view.inner.MyMultiAdapter;
import com.zzy.core.view.inner.SpaceItemDecoration;

import java.util.List;

/**
 * @author zzy
 * @date 2018/2/27
 */
public class NormalPageRender implements com.zzy.core.view.render.page.PageRender {
    public static final String TAG = "NormalPageRender";
    private View rootView;
    private Context context;
    private RecyclerView recyclerView;
    private MyMultiAdapter adapter;
    private SpaceItemDecoration itemDecoration;
    public NormalPageRender(Context context) {
        this.context = context;
    }

    @Override
    public void render(ViewGroup container, Page page) {
        if(page==null){
            return;
        }
        if(rootView==null){
            rootView = LayoutInflater.from(context).inflate(R.layout.elf_page_content_normal, container, false);
            container.addView(rootView);
        }
        try {
            Page cloneP = page.cloneMe(page);
            /*统计*/
            StatisticsTool.sighElfPage(cloneP);
            renderViews(cloneP);
        } catch (Exception e) {
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    private void renderViews(Page page) throws Exception{
        if(recyclerView == null) {
            recyclerView = rootView.findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        itemDecoration = new SpaceItemDecoration(context,page.getBody().getDataList());
        recyclerView.addItemDecoration(itemDecoration);
        /*adapter*/
        adapter = new MyMultiAdapter(context,page.getBody().getDataList());
        SparseArray<TemplateRender> templateRenderList = ElfProxy.getInstance().getBinder().getTemplateRenderList(context,page);
        for(int i = 0; i< templateRenderList.size(); i++){
            TemplateRender templateRender = templateRenderList.valueAt(i);
            adapter.addItemViewDelegate(templateRender);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ElfProxy.getInstance().getBinder().onShowImage(context, Uri.parse(page.getBackground()),recyclerView);
    }
}

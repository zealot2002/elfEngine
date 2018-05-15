package com.zzy.core.view.render.page.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzy.core.ElfProxy;
import com.zzy.core.R;
import com.zzy.core.model.Page;
import com.zzy.core.model.Section;
import com.zzy.core.statis.StatisticsTool;
import com.zzy.core.utils.L;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.inner.MyMultiAdapter;
import com.zzy.core.view.inner.SpaceItemDecoration;
import com.zzy.core.view.inner.WaterfallOnScrollListener;
import com.zzy.core.view.render.TemplateRender;
import com.zzy.core.view.render.page.WaterfallPageRender;

import java.util.List;

/**
 * @author zzy
 * @date 2018/2/27
 */
public class RefreshPageRender implements WaterfallPageRender<Section> {
    public static final String TAG = "RefreshPageRender";
    private View rootView;
    private Context context;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyMultiAdapter adapter;
    private Page currentPage;
    private SpaceItemDecoration itemDecoration;
    private WaterfallPageRender.EventListener eventListener;
    private int pageNum = 1;
    private WaterfallOnScrollListener onScrollListener;
    private boolean isLoadOver = false;
    public RefreshPageRender(Context context) {
        this.context = context;
    }
    @Override
    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void render(ViewGroup container, Page page) {
        if(refreshLayout !=null){
            refreshLayout.finishRefresh();
        }
        if(onScrollListener !=null){
            onScrollListener.reset();
        }
        if(page==null){
            return;
        }
        if(rootView==null){
            rootView = LayoutInflater.from(context).inflate(R.layout.elf_page_content_refresh, container, false);
            container.addView(rootView);
        }
        if(currentPage!=null&&currentPage.equals(page)){
            L.d(TAG,"render: no data changed!");
            return;
        }
        currentPage = page;
        try {
            Page cloneP = page.cloneMe(page);
            /*统计*/
            StatisticsTool.sighElfPage(cloneP);
            renderViews(cloneP);
        } catch (Exception e) {
            currentPage = null;
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    @Override
    public void appendUpdateData(List<Section> list) {
        if(onScrollListener !=null){
            onScrollListener.reset();
        }
        if(list.isEmpty()){
            isLoadOver = true;
            return;
        }

        currentPage.getBody().getDataList().addAll(list);
        Page cloneP = currentPage.cloneMe(currentPage);
        /*统计*/
        StatisticsTool.sighElfPage(cloneP);
        adapter.setDataList(cloneP.getBody().getDataList());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        },10);
    }

    private void renderViews(Page page) throws Exception{
        if(recyclerView == null){
            recyclerView = rootView.findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        /*load more*/
            onScrollListener = new WaterfallOnScrollListener() {
                @Override
                public void onLoadMore() {
                    L.e(TAG,"onLoadMore ");
                    if(isLoadOver){
                        return;
                    }
                    if(eventListener!=null){
                        eventListener.onLoadMore(++pageNum);
                    }
                }
            };
            recyclerView.addOnScrollListener(onScrollListener);
        /*reload*/
            refreshLayout = rootView.findViewById(R.id.refresh);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    if(eventListener !=null){
                        isLoadOver = false;
                        pageNum = 1;
                        onScrollListener.reset();
                        eventListener.onReload();
                    }
                }
            });
        }
    /*below refresh every time*/
        if(itemDecoration!=null){
            recyclerView.removeItemDecoration(itemDecoration);
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

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
import com.zzy.core.ElfConstact;
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
import com.zzy.core.view.render.element.impl.ElementRender;
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
    private ElementRender elementRender;
/****************************************************************************************************/
    public RefreshPageRender(Context context) {
        this.context = context;
        elementRender = new ElementRender(context);
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
            /*如果有title，先绘制title，顺序不能错*/
            if(page.getTitle()!=null){
                elementRender.render(container,page.getTitle());
            }
            /*接着add body*/
            rootView = LayoutInflater.from(context).inflate(R.layout.elf_page_content_refresh, container, false);
            container.addView(rootView);

            /*如果有footer，绘制footer，顺序不能错*/
            if(page.getFooter()!=null){
                elementRender.render(container,page.getFooter());
            }
        }
        if(currentPage!=null&&currentPage.equals(page)){
            L.d(TAG,"render: no data changed!");
            return;
        }
        currentPage = page;
        try {
            /*
                为了达到“如果data相等，就不重复绘制”的目的，
                这里需要把统计的标记打在clone数据上
            */
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
        recyclerView.post(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
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
        SparseArray<ElfConstact.TemplateRender> templateRenderList = ElfProxy.getInstance().getHook().getTemplateRenderList(context,page);
        for(int i = 0; i< templateRenderList.size(); i++){
            ElfConstact.TemplateRender templateRender = templateRenderList.valueAt(i);
            adapter.addItemViewDelegate(templateRender);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(page.getBackground()!=null){
            ElfProxy.getInstance().getHook().onSetResource(context, Uri.parse(page.getBackground()),recyclerView);
        }
    }
}

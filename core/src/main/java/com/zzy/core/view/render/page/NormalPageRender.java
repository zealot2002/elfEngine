package com.zzy.core.view.render.page;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.core.ElfEngineProxy;
import com.zzy.core.R;
import com.zzy.core.model.Page;
import com.zzy.core.statis.StatisticsTool;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.Engine;
import com.zzy.core.view.inner.MyMultiAdapter;
import com.zzy.core.view.inner.SpaceItemDecoration;

/**
 * @author zzy
 * @date 2018/2/27
 */
public class NormalPageRender implements PageRender{
    public static final String TAG = "MultiRecyclePageRender";
    private View rootView;
    private Context context;
    private RecyclerView recyclerView;
    private MyMultiAdapter adapter;
    private Page currentPage;
    private SpaceItemDecoration itemDecoration;
    private boolean isLoadOver = false;
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
        for(int i = 0; i< ElfEngineProxy.getInstance().getBinder().getEngineList().size(); i++){
            Engine engine = ElfEngineProxy.getInstance().getBinder().getEngineList().get(i);
            adapter.addItemViewDelegate(engine);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ElfEngineProxy.getInstance().getBinder().showImage(context,page.getBackground(),recyclerView);
    }
}

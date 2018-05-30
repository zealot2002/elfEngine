package com.zzy.elf_template.template.engine.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.core.model.Item;
import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.L;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.inner.recycleAdapter.CommonBaseAdapter;
import com.zzy.core.view.inner.recycleAdapter.ViewHolder;
import com.zzy.elf_template.R;
import com.zzy.elf_template.template.WidgetHelper;
import com.zzy.elf_template.template.engine.Engine;

import java.util.List;

/**
 * @author zzy
 * @date 2018/2/11
 */

public class RecyclerViewEngine extends Engine {
    public static final String TAG = "RecyclerViewEngine";
    public static final int RECYCLER_HORIZONTAL = 1;
    public static final int RECYCLER_VERTICAL = 2;
    public static final int RECYCLER_GRID_3 = 3;
    public static final int RECYCLER_GRID_4 = 4;

    private Context context;
    private int templateId,layoutId,itemLayoutId,layoutManagerType;
/*****************************************************************************************************/
    private RecyclerViewEngine(Context context,
                               int templateId,
                               int layoutId,
                               int itemLayoutId,
                               int layoutManagerType
                                        ) {
        this.context = context;
        this.templateId = templateId;
        this.layoutId = layoutId;
        this.itemLayoutId = itemLayoutId;
        this.layoutManagerType = layoutManagerType;
    }

    public RecyclerViewEngine() {}
    @Override
    public int getItemViewLayoutId() {
        return layoutId;
    }

    @Override
    public boolean isForViewType(Object obj, int position) {
        if(obj instanceof Section){
            return ((Section)obj).getTemplateId()==templateId;
        }else{
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object obj, int position) {
        ViewGroup rootView = holder.getView(R.id.elf_rootView);
        render(rootView, obj);
    }

    @Override
    public void render(ViewGroup rootView,Object obj) {
        L.e(TAG,"render");
        try{
            RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager layoutManager = makeLayoutManager(layoutManagerType);
            recyclerView.setLayoutManager(layoutManager);
            MyAdapter adapter = new MyAdapter(context,((Section)obj).getItemList(),false);
            recyclerView.setAdapter(adapter);
            recyclerView.setFocusable(false);
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    private RecyclerView.LayoutManager makeLayoutManager(int layoutManagerType) {
        if(layoutManagerType == RECYCLER_HORIZONTAL){
            return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        }else if(layoutManagerType == RECYCLER_VERTICAL){
            return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        }else if(layoutManagerType == RECYCLER_GRID_3){
            return new GridLayoutManager(context,3);
        }else if(layoutManagerType == RECYCLER_GRID_4){
            return new GridLayoutManager(context,4);
        }
        return null;
    }

    @Override
    public Engine newInstance(Context context, int templateId, int layoutId, Object... args) {
        return new RecyclerViewEngine(context,templateId,layoutId,(int)args[0],(int)args[1]);
    }

    public class MyAdapter extends CommonBaseAdapter<Item> {

        public MyAdapter(Context context, List<Item> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder, Item item, int position) {
            View rootView = holder.getView(R.id.elf_rootView);
            List<Widget> widgets = item.getWidgetList();
            WidgetHelper.fillWidgets(context,rootView,widgets);
        }

        @Override
        protected int getItemLayoutId() {
            return itemLayoutId;
        }
    }
}

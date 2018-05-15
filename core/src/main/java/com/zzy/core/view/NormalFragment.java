package com.zzy.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.commonlib.core.BusHelper;
import com.zzy.core.ElfConstact;
import com.zzy.core.R;
import com.zzy.core.model.Page;
import com.zzy.core.view.render.footer.FooterRender;
import com.zzy.core.view.render.header.HeaderRender;
import com.zzy.core.view.render.page.impl.NormalPageRender;
/**
 * @author zzy
 * @date 2018/2/28
 */
public class NormalFragment extends Fragment{
    private static final String TAG = "NormalFragment";
    private View rootView;
    private Context context;
    private ViewGroup container;

    private com.zzy.core.view.render.page.PageRender pageRender;/*page and body*/
    private HeaderRender headerRender;
    private FooterRender footerRender;
    private ElfConstact.NormalDataProvider dataProvider;
/********************************************************************************************************/
    public NormalFragment(){}

    @SuppressLint("ValidFragment")
    public NormalFragment(ElfConstact.NormalDataProvider dataProvider){
        this.dataProvider = dataProvider;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        rootView = inflater.inflate(R.layout.elf_fragment_page, container, false);
        BusHelper.getBus().register(this);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(dataProvider!=null){
            dataProvider.onGetDataEvent(context,new ElfConstact.Callback() {
                @Override
                public void onCallback(boolean bResult, Object data) {
                    Page page = (Page) data;
                    updatePage(page);
                }
            });
        }
    }

    public void updatePage(Page page) {
        if(container == null){
            container = rootView.findViewById(R.id.container);
            if(pageRender == null){
                pageRender = new NormalPageRender(context);
            }
        }
        pageRender.render(container,page);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        BusHelper.getBus().unregister(this);
    }
}

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

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.core.ElfConstact;
import com.zzy.core.R;
import com.zzy.core.constants.BusConstants;
import com.zzy.core.constants.CommonConstants;
import com.zzy.core.model.Page;
import com.zzy.core.utils.L;
import com.zzy.core.view.render.element.impl.ElementRender;
import com.zzy.core.view.render.page.PageGroupRender;
import com.zzy.core.view.render.page.PageRender;
import com.zzy.core.view.render.page.impl.NormalPageRender;
import com.zzy.core.view.render.page.impl.PageListTop1PageRender;

/**
 * @author zzy
 * @date 2018/2/28
 */
public class NormalFragment extends Fragment{
    private static final String TAG = "NormalFragment";
    private View rootView;
    private Context context;
    private ViewGroup container;
    private String pageCode;
    private PageRender pageRender;
    private ElfConstact.PageAdapter adapter;
/********************************************************************************************************/
    public NormalFragment(){}

    @SuppressLint("ValidFragment")
    public NormalFragment(String pageCode,ElfConstact.PageAdapter adapter){
        this.pageCode = pageCode;
        this.adapter = adapter;
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
        if(adapter!=null){
            adapter.getPageData(context,1,new ElfConstact.Callback() {
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
                if(page.getType().equals(ElfConstact.PAGE_TYPE_SINGLE_PAGE)){
                    pageRender = new NormalPageRender(context);
                }else if(page.getType().equals(ElfConstact.PAGE_TYPE_PAGE_GROUP)){
                    pageRender = new PageListTop1PageRender(context,NormalFragment.this,adapter);
                }
            }
        }
        pageRender.render(container,page);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe(thread = EventThread.IMMEDIATE, tags = {@Tag(value = BusConstants.BUS_EVENT_PAGEGROUP_TRANSFER_PAGE)})
    public void onPageGroupTransferPage(String s){
        L.e(TAG,"onPageGroupTransferPage");
        if(s!=null){
            if(pageRender instanceof PageGroupRender){
                String pageGroupCode = s.substring(0,s.indexOf(CommonConstants.SPECIAL_LETTER));
                if(this.pageCode.equals(pageGroupCode)){
                    String pageCode = s.substring(s.indexOf(CommonConstants.SPECIAL_LETTER)+1,s.length());
                    ((PageGroupRender) pageRender).transferPage(pageCode);
                }
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        BusHelper.getBus().unregister(this);
    }
}

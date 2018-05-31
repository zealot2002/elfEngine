package com.zzy.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.core.ElfConstact;
import com.zzy.core.ElfProxy;
import com.zzy.core.R;
import com.zzy.core.base.BaseLoadingFragment;
import com.zzy.core.constact.PageConstact;
import com.zzy.core.constants.BusConstants;
import com.zzy.core.constants.CommonConstants;
import com.zzy.core.model.Page;
import com.zzy.core.presenter.PagePresenter;
import com.zzy.core.utils.L;
import com.zzy.core.view.render.page.PageGroupRender;
import com.zzy.core.view.render.page.PageRender;
import com.zzy.core.view.render.page.WaterfallPageRender;
import com.zzy.core.view.render.page.impl.BottomRefreshPageRender;

import java.util.ArrayList;

/**
 * @author zzy
 * @date 2018/2/28
 */
public class RefreshFragment extends BaseLoadingFragment implements PageConstact.View{
    private static final String TAG = "RefreshFragment";
    private View rootView;
    private Context context;
    private PagePresenter presenter;
    private String pageCode;
    private boolean needReload;
    private ViewGroup container;
    private PageRender pageRender;
    private ElfConstact.PageAdapter dataProvider;
/********************************************************************************************************/
    public RefreshFragment(){}

    @SuppressLint("ValidFragment")
    public RefreshFragment(String pageCode,ElfConstact.PageAdapter dataProvider){
        this.pageCode = pageCode;
        this.dataProvider = dataProvider;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.e(TAG,"onCreateView");
        BusHelper.getBus().register(this);
        context = getActivity();
        contentViewResId = R.layout.elf_fragment_page;
        disconnectViewResId = ElfProxy.getInstance().getHook().getDisconnectLayoutId();
        rootView = super.onCreateView(inflater,container,savedInstanceState);

        presenter = new PagePresenter(this);
        presenter.getPageData(context, true,1,dataProvider);
        return rootView;
    }

    @Override
    public void reload(boolean bShow) {
        L.e(TAG,pageCode+" reload");
        presenter.getPageData(context,bShow,1,dataProvider);
    }

    @Override
    public void updatePage(Page page, int pageNum) throws Exception{
        super.updateUI(null);
        if(page.getType() == ElfConstact.PAGE_TYPE_PAGE_GROUP){
            throw new Exception("RefreshFragment do not support the pageGroup type!");
        }
        if(container == null){
            container = rootView.findViewById(R.id.container);
//            pageRender = new RefreshPageRender(context);
            pageRender = new BottomRefreshPageRender(context);

            ((WaterfallPageRender) pageRender).setEventListener(waterfallEventListener);
        }
        if(pageNum == 1){
            pageRender.render(container,page);
        }else{
            if(page.getBody()!=null){
                ((WaterfallPageRender) pageRender).appendUpdateData(page.getBody().getDataList());
            }else{
                ((WaterfallPageRender) pageRender).appendUpdateData(new ArrayList());
            }
        }
    }

    WaterfallPageRender.EventListener waterfallEventListener = new WaterfallPageRender.EventListener() {
        @Override
        public void onReload() {
            reload(false);
        }
        @Override
        public void onLoadMore(int pageNum) {
            presenter.getPageData(context,true,pageNum,dataProvider);
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        L.e(TAG,"onHiddenChanged pageCode:"+pageCode+" hidden："+hidden);
        if(!hidden){
            if(needReload){
                needReload = false;
                reload(true);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Subscribe(thread = EventThread.IMMEDIATE, tags = {@Tag(value = BusConstants.BUS_EVENT_RELOAD_PAGE)})
    public void onReload(String s){
        L.e(TAG,pageCode+" onReload");
        if(s.equals(pageCode)){
            needReload = true;
        }
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

    @Override
    public void showDisconnect() {
        /*如果一进来就断网，或者reload时候断网，显示全屏断网layout*/
        super.showDisconnect();
    }

    @Override
    public void showLoadingError() {
        super.showLoadingError();
        /*如果加载更多时候断网，显示列表的断网layout*/
        if(pageRender!=null){
            ((WaterfallPageRender) pageRender).showDisconnect();
        }
    }
}

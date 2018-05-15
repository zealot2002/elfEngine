package com.zzy.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.commonlib.core.BusHelper;
import com.zzy.core.ElfConstact;
import com.zzy.core.R;
import com.zzy.core.base.BaseLoadingFragment;
import com.zzy.core.constact.PageConstact;
import com.zzy.core.constants.CommonConstants;
import com.zzy.core.model.Page;
import com.zzy.core.presenter.PagePresenter;
import com.zzy.core.utils.L;
import com.zzy.core.view.render.footer.FooterRender;
import com.zzy.core.view.render.header.HeaderRender;
import com.zzy.core.view.render.page.PageGroupRender;
import com.zzy.core.view.render.page.PageRender;
import com.zzy.core.view.render.page.WaterfallPageRender;
import com.zzy.core.view.render.page.impl.RefreshPageRender;

/**
 * @author zzy
 * @date 2018/2/28
 */
public class RefreshFragment extends BaseLoadingFragment implements PageConstact.View{
    private static final String TAG = "ElfFragment";
    private View rootView;
    private Context context;
    private PagePresenter presenter;
    private String pageCode;
    private boolean needReload;
    private ViewGroup container;

    private PageRender pageRender;/*page and body*/
    private HeaderRender headerRender;
    private FooterRender footerRender;

    private ElfConstact.RefreshDataProvider dataProvider;
/********************************************************************************************************/
    public RefreshFragment(){}

    @SuppressLint("ValidFragment")
    public RefreshFragment(ElfConstact.RefreshDataProvider dataProvider){
        this.dataProvider = dataProvider;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater,container,savedInstanceState);
        context = getActivity();
        setContentViewLayoutId(R.layout.elf_fragment_page);

        presenter = new PagePresenter(this);
        presenter.getPageData(context, true,1,dataProvider);

        BusHelper.getBus().register(this);
        return rootView;
    }

    @Override
    public void reload(boolean bShow) {
        L.e(TAG,pageCode+" reload");
        presenter.getPageData(context,bShow,1,dataProvider);
    }

    @Override
    public void updatePage(Page page, int pageNum) {
        super.updateUI(null);
        if(container == null){
            container = rootView.findViewById(R.id.container);
            if(pageRender == null){
                pageRender = new RefreshPageRender(context);
                ((WaterfallPageRender) pageRender).setEventListener(waterfallEventListener);
            }
        }
        if(pageNum == 1){
//            headerRender.render();  按照顺序addview 刷新的时候，title也会变，但是刷新动画显示在title下面
            pageRender.render(container,page);
//            footerRender.render();
        }else{
            ((WaterfallPageRender) pageRender).appendUpdateData(page.getBody().getDataList());
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

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        Mylog.e(TAG,"onHiddenChanged");
//        if(!hidden){
//            lazyLoad();
//        }
//    }
//
//    private void lazyLoad() {
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG,"onResume");
        if(needReload){
            needReload = false;
            reload(true);
        }
    }
//    @Subscribe(thread = EventThread.IMMEDIATE, tags = {@Tag(value = BusConstants.BUS_EVENT_RELOAD_PAGE)})
    public void onReload(String s){
        L.e(TAG,pageCode+" onReload");
//        if(s.equals(pageCode)){
            needReload = true;
//        }
    }
//    @Subscribe(thread = EventThread.IMMEDIATE, tags = {@Tag(value = BusConstants.BUS_EVENT_PAGEGROUP_TRANSFER_PAGE)})
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

package com.zzy.core.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zzy.commonlib.utils.NetUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.constact.PageConstact;
import com.zzy.core.model.Page;
import com.zzy.core.utils.L;
import com.zzy.core.utils.MyExceptionHandler;

/**
 * @author zzy
 * @Description:
 * @date 2017/12/06 15:06:30
 */

public class PagePresenter implements PageConstact.Presenter {
    private static final String TAG = "PagePresenter";
    private final PageConstact.View view;
/****************************************************************************************************/
    public PagePresenter(@NonNull PageConstact.View view) {
        this.view = view;
    }
    @Override
    public void start() {
    }

    @Override
    public void getPageData(final Context context, boolean bShow,final int pageNum, ElfConstact.PageAdapter adapter) {
        L.e(TAG,"getPageData pageNum:"+pageNum);
        if (!NetUtils.isNetworkAvailable(context)) {
            showDisconnectByPageNum(pageNum);
            return;
        }
        if(bShow){
            view.showLoading();
        }
        adapter.getPageData(context, pageNum, new ElfConstact.Callback() {
            @Override
            public void onCallback(boolean bResult, Object data) {
                L.e(TAG,"onCallback bResult:"+bResult);
                view.closeLoading();
                try{
                    if(bResult){
                        Page page = (Page) data;
                        view.updatePage(page,pageNum);
                    }else {
                        L.e(TAG,"返回 err:" +data.toString());
                        showDisconnectByPageNum(pageNum);
                    }
                }catch(Exception e){
                    MyExceptionHandler.handle(context,TAG,e);
                    showDisconnectByPageNum(pageNum);
                }
            }
        });
    }
    private void showDisconnectByPageNum(int pageNum){
        if(pageNum > 1){
            view.showLoadingError();
        }else{
            view.showDisconnect();
        }
    }
}
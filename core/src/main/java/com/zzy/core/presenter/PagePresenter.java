package com.zzy.core.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zzy.commonlib.utils.NetUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.constact.PageConstact;
import com.zzy.core.model.Page;
import com.zzy.core.utils.L;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.utils.MyToast;

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
        if (!NetUtils.isNetworkAvailable(context)) {
            //do nothing..just go on
            view.showDisconnect();
            return;
        }
        if(bShow){
            view.showLoading();
        }
        adapter.getPageData(context, pageNum, new ElfConstact.Callback() {
            @Override
            public void onCallback(boolean bResult, Object data) {
                view.closeLoading();
                try{
                    if(bResult){
                        Page page = (Page) data;
                        view.updatePage(page,pageNum);
                    }else {
                        MyToast.show(context," err:"+data.toString());
                        L.e(TAG,"返回 err:" +data.toString());
                        view.showDisconnect();
                    }
                }catch(Exception e){
                    MyExceptionHandler.handle(context,TAG,e);
                    view.showDisconnect();
                }
            }
        });
    }
}
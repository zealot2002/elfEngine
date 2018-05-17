package com.zzy.core;

import android.support.v4.app.Fragment;

import com.zzy.commonlib.core.BusHelper;
import com.zzy.core.constants.BusConstants;
import com.zzy.core.constants.CommonConstants;
import com.zzy.core.view.NormalFragment;
import com.zzy.core.view.RefreshFragment;

import java.util.List;

/**
 * @author zzy
 * @date 2018/5/12
 */

public class ElfProxy implements ElfConstact.Req{
    private ElfConstact.Hook hook;
/*******************************************************************************************************/
    public static ElfProxy getInstance(){
        return LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final ElfProxy ourInstance = new ElfProxy();
    }
    private ElfProxy() {}

    public ElfConstact.Hook getHook() {
        return hook;
    }

    @Override
    public void setDebugMode(boolean b) {
        CommonConstants.isDebug = b;
    }

    @Override
    public void setHook(ElfConstact.Hook hook) {
        this.hook = hook;
    }

    public Fragment makeNormalPage(String pageCode,ElfConstact.DataProvider dataProvider) {
        return new NormalFragment(pageCode,dataProvider);
    }

    @Override
    public Fragment makeRefreshPage(String pageCode,ElfConstact.DataProvider dataProvider) {
        return new RefreshFragment(pageCode,dataProvider);
    }

    @Override
    public void notifyDataSetChanged(String pageCode) {
        if(pageCode!=null){
            BusHelper.getBus().post(BusConstants.BUS_EVENT_RELOAD_PAGE,pageCode);
        }
    }

    @Override
    public void notifyDataSetChanged(List<String> pageCodeList) {
        if(pageCodeList!=null){
            for(int i=0;i<pageCodeList.size();++i){
                notifyDataSetChanged(pageCodeList.get(i));
            }
        }
    }

    @Override
    public void notifyPageGroupTransferPage(String pageGroupCode, String pageCode) {
        if(pageGroupCode!=null
                &&pageCode!=null){
            BusHelper.getBus().post(BusConstants.BUS_EVENT_PAGEGROUP_TRANSFER_PAGE,
                    pageGroupCode+CommonConstants.SPECIAL_LETTER+pageCode);
        }
    }
}

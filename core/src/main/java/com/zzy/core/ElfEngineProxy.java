package com.zzy.core;

import android.support.v4.app.Fragment;

import com.zzy.core.constants.CommonConstants;
import com.zzy.core.view.ElfFragment;

/**
 * @author zzy
 * @date 2018/5/12
 */

public class ElfEngineProxy implements ElfEngineConstact.Req{
    private ElfEngineConstact.Binder binder;
/*******************************************************************************************************/
    public static ElfEngineProxy getInstance(){
        return LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final ElfEngineProxy ourInstance = new ElfEngineProxy();
    }
    private ElfEngineProxy() {}
    public ElfEngineConstact.Binder getBinder() {
        return binder;
    }


    @Override
    public void setDebugMode(boolean b) {
        CommonConstants.isDebug = b;
    }

    @Override
    public void setBinder(ElfEngineConstact.Binder binder) {
        this.binder = binder;
    }

    @Override
    public Fragment makeNormalElfPage(ElfEngineConstact.DataProvider dataProvider) {
        return new ElfFragment(dataProvider);
    }
}

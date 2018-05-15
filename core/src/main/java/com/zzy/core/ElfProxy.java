package com.zzy.core;

import android.support.v4.app.Fragment;

import com.zzy.core.constants.CommonConstants;
import com.zzy.core.view.NormalFragment;
import com.zzy.core.view.RefreshFragment;

/**
 * @author zzy
 * @date 2018/5/12
 */

public class ElfProxy implements ElfConstact.Req{
    private ElfConstact.Binder binder;
/*******************************************************************************************************/
    public static ElfProxy getInstance(){
        return LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final ElfProxy ourInstance = new ElfProxy();
    }
    private ElfProxy() {}
    public ElfConstact.Binder getBinder() {
        return binder;
    }


    @Override
    public void setDebugMode(boolean b) {
        CommonConstants.isDebug = b;
    }

    @Override
    public void setBinder(ElfConstact.Binder binder) {
        this.binder = binder;
    }

    public Fragment makeNormalPage(ElfConstact.NormalDataProvider dataProvider) {
        return new NormalFragment(dataProvider);
    }

    @Override
    public Fragment makeRefreshPage(ElfConstact.RefreshDataProvider dataProvider) {
        return new RefreshFragment(dataProvider);
    }
}

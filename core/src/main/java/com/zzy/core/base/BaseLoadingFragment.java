package com.zzy.core.base;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.core.ElfProxy;
import com.zzy.core.R;
import com.zzy.core.utils.L;
/**
 * @author zzy
 * @date 2018/2/27
 */

public class BaseLoadingFragment extends Fragment implements BaseLoadingView {
    private static final String TAG = "BaseLoadingFragment";

    public void showLoading() {
    }
    public void closeLoading() {
    }

    @Override
    public void showDisconnect() {
    }
    @Override
    public void showLoadingError() {
    }
    private void hideAll(){
    }
    @Override
    public void reload(boolean bShow) {}

    @Override
    public void updateUI(Object o) {
    }

}

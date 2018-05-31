package com.zzy.core.base;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.core.R;


/**
 * @author zzy
 * @date 2018/2/27
 */

public class BaseLoadingFragment extends Fragment implements BaseLoadingView {
    private static final String TAG = "BaseLoadingFragment";
    protected int contentViewResId,disconnectViewResId;

    private ViewGroup rootView;
    private View contentView,disconnectView;
/*********************************************************************************************************/
    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.elf_base_loading_fragment, container, false);
        initViews();
        return rootView;
    }


    protected void initViews(){
        contentView = getActivity().getLayoutInflater().inflate(contentViewResId,null);
        disconnectView = getActivity().getLayoutInflater().inflate(disconnectViewResId,null);

        rootView.addView(contentView);
        rootView.addView(disconnectView);

        disconnectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload(true);
            }
        });
        hideAll();
    }

    //    XTipDialog elf_loading;
    public void showLoading() {
//        elf_loading = XTip.elf_loading(getHoldingActivity());
//        LoadingUtils.getInstance(context).showLoading(context);
//        ElfProxy.getInstance().getBinder().onShowLoading(context);

    }
    public void closeLoading() {
//        XTip.dismiss(elf_loading);
//        LoadingUtils.getInstance(context).dissMissDialog();
//        ElfProxy.getInstance().getBinder().onCloseLoading(context);
    }

    @Override
    public void showDisconnect() {
        hideAll();
        disconnectView.setVisibility(View.VISIBLE);
    }
    @Override
    public void showLoadingError() {}



    private void hideAll(){
        for(int i=0;i<rootView.getChildCount();i++){
            rootView.getChildAt(i).setVisibility(View.GONE);
        }
    }
    //子类复写此方法
    @Override
    public void reload(boolean b) {}

    @Override
    @CallSuper
    public void updateUI(Object o) {
        hideAll();
        contentView.setVisibility(View.VISIBLE);
    }

}

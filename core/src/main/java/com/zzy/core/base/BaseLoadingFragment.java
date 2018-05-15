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

    private View root,contentView;
    private FrameLayout flLoadingErrorRoot,flLoadingRoot;
    private LinearLayout llDisconnectRoot;
    private Context context;


    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.elf_base_loading_fragment, container, false);
        initViews();
        return root;
    }
    protected void setRoot(View root){
        this.root = root;
    }
    protected void initViews(){
        flLoadingRoot = root.findViewById(R.id.flLoadingRoot);
        llDisconnectRoot = root.findViewById(R.id.llDisconnectRoot);
        flLoadingErrorRoot = root.findViewById(R.id.flLoadingErrorRoot);
        (llDisconnectRoot.findViewById(R.id.btnReload)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload(true);
            }
        });
        (flLoadingErrorRoot.findViewById(R.id.btnErrorReload)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload(true);
            }
        });
        hideAll();
    }


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    //    XTipDialog elf_loading;
    public void showLoading() {
//        elf_loading = XTip.elf_loading(getHoldingActivity());
//        LoadingUtils.getInstance(context).showLoading(context);
        L.e(TAG," showLoading");
//        ElfProxy.getInstance().getBinder().onShowLoading(context);

    }
    public void closeLoading() {
//        XTip.dismiss(elf_loading);
//        LoadingUtils.getInstance(context).dissMissDialog();
        L.e(TAG," closeLoading");
//        ElfProxy.getInstance().getBinder().onCloseLoading(context);
    }

    @Override
    public void showDisconnect() {
        hideAll();
        llDisconnectRoot.setVisibility(View.VISIBLE);
    }
    @Override
    public void showLoadingError() {
        hideAll();
        flLoadingErrorRoot.setVisibility(View.VISIBLE);
    }
    private void hideAll(){
        for(int i=0;i<flLoadingRoot.getChildCount();i++){
            flLoadingRoot.getChildAt(i).setVisibility(View.GONE);
        }
    }
    //设置content layoutId
    public void setContentViewLayoutId(int resId) throws RuntimeException {
        if(flLoadingRoot==null){
            throw new RuntimeException("此方法必须在父类先初始化之后调用");
        }
        if(contentView!=null){
            flLoadingRoot.removeView(contentView);
        }
        contentView = getActivity().getLayoutInflater().inflate(resId,null);
        contentView.setVisibility(View.GONE);
        flLoadingRoot.addView(contentView);
    }
    //子类复写此方法
    @Override
    public void reload(boolean bShow) {}

    //子类复写此方法，需要调用super.updateUI
    @Override
    @CallSuper
    public void updateUI(Object o) {
        hideAll();
        contentView.setVisibility(View.VISIBLE);
    }

}

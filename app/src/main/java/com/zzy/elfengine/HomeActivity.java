package com.zzy.elfengine;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.zzy.commonlib.base.BaseActivity;
import com.zzy.commonlib.util.FileUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.ElfProxy;
import com.zzy.core.model.Page;

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment_container);

        Fragment f = getNormalFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_framelayout,f);
        ft.commit();
    }

    private Fragment getNormalFragment(){
        return ElfProxy.getInstance().makeNormalPage(new ElfConstact.NormalDataProvider() {
            @Override
            public void onGetDataEvent(Context context, ElfConstact.Callback callback) {
                try{
                    String data = FileUtils.readFileFromAssets(HomeActivity.this,"home.json");
                    Page page = PageJsonParser.parse(data);
                    callback.onCallback(true,page);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private Fragment getRefreshFragment() {
        return ElfProxy.getInstance().makeRefreshPage(new ElfConstact.RefreshDataProvider() {
            @Override
            public void onGetDataEvent(Context context, int pageNum, ElfConstact.Callback callback) {
                try{
                    String data = FileUtils.readFileFromAssets(HomeActivity.this,"home.json");
                    Page page = PageJsonParser.parse(data);
                    callback.onCallback(true,page);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

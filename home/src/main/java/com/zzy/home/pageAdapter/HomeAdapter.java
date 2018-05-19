package com.zzy.home.pageAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.zzy.common.elf.ElfJsonParser;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.model.Page;

/**
 * @author zzy
 * @date 2018/5/17
 */

public class HomeAdapter implements ElfConstact.PageAdapter{
    @Override
    public void getPageData(Context context, int pageNum, ElfConstact.Callback callback) {
        try{
            String data = FileUtils.readFileFromAssets(context,"homeFragment.json");
            Page page = ElfJsonParser.parse(data);
            callback.onCallback(true,page);
        }catch(Exception e){
            e.printStackTrace();
            callback.onCallback(false,e.toString());
        }
    }

    @Override
    public Fragment getFragment(Context context, String pageCode) {
        return null;
    }
}

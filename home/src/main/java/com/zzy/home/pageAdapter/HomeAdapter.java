package com.zzy.home.pageAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.zzy.common.elf.ElfJsonParser;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.model.Page;
import com.zzy.core.model.Section;
import com.zzy.core.view.element.body.Body;
import com.zzy.core.view.element.body.PageBody;

import java.util.ArrayList;

/**
 * @author zzy
 * @date 2018/5/17
 */

public class HomeAdapter implements ElfConstact.PageAdapter{
    @Override
    public void getPageData(Context context, int pageNum, ElfConstact.Callback callback) {
        if(pageNum>5){
            callback.onCallback(true,new Page());
            return;
        }
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

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

public class NetLoanAdapter implements ElfConstact.PageAdapter{
    @Override
    public void getPageData(Context context, int pageNum, ElfConstact.Callback callback) {
        try{
            //for test ，只提供2页数据
            if(pageNum<3){
                String data = FileUtils.readFileFromAssets(context,"netLoanFragment.json");
                Page page = ElfJsonParser.parse(data);
                callback.onCallback(true,page);
            }else{
                callback.onCallback(true,new Page());
            }
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

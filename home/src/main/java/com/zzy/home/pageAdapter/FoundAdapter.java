package com.zzy.home.pageAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.zzy.common.elf.ElfConstants;
import com.zzy.common.elf.ElfJsonParser;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.core.ElfConstact;
import com.zzy.core.ElfProxy;
import com.zzy.core.model.Page;

import static com.zzy.common.elf.ElfConstants.ELF_PAGE_NET_LOAN;

/**
 * @author zzy
 * @date 2018/5/17
 */

public class FoundAdapter implements ElfConstact.PageAdapter{
    @Override
    public void getPageData(Context context, int pageNum, ElfConstact.Callback callback) {
        try{
            String data = FileUtils.readFileFromAssets(context,"foundFragment.json");
            Page page = ElfJsonParser.parse(data);
            callback.onCallback(true,page);
        }catch(Exception e){
            e.printStackTrace();
            callback.onCallback(false,e.toString());
        }
    }

    @Override
    public Fragment getFragment(Context context, String pageCode) {
        if(pageCode.equals(ElfConstants.ELF_PAGE_NET_LOAN)){
            return ElfProxy.getInstance().makeRefreshPage(pageCode,new NetLoanAdapter());
        }else if(pageCode.equals(ElfConstants.ELF_PAGE_INSURANCE)){
            return ElfProxy.getInstance().makeNormalPage(pageCode,new InsuranceAdapter());
        }else if(pageCode.equals(ElfConstants.ELF_PAGE_FUND)){
            return ElfProxy.getInstance().makeNormalPage(pageCode,new FundAdapter());
        }
        return new Fragment();
    }
}

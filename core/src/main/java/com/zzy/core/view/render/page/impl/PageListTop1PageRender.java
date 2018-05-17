package com.zzy.core.view.render.page.impl;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.zzy.commonlib.utils.DisplayUtils;
import com.zzy.core.ElfProxy;
import com.zzy.core.R;
import com.zzy.core.model.Page;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.inner.MyFragmentPagerAdapter;
import com.zzy.core.view.inner.MyViewPaper;
import com.zzy.core.view.render.element.impl.ElementRender;
import com.zzy.core.view.render.page.PageGroupRender;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/2/27
 */
public class PageListTop1PageRender implements PageGroupRender {
    public static final String TAG = "PageListTop1PageRender";
    //page group
    private View rootView;
    private Context context;
    private Fragment rootFragment;
    private LinearLayout llPageGroup;
    private MyFragmentPagerAdapter myPagerAdapter;
    private MyViewPaper mViewPager;
    private List<Page> pageList;
    private FragmentContainerHelper fragmentContainerHelper;
    private ElementRender titleRender,headerRender,footerRender;
/**************************************************************************************************/
    public PageListTop1PageRender(Context context, Fragment rootFragment) {
        this.context = context;
        this.rootFragment = rootFragment;
    }
    @Override
    public void render(ViewGroup container, Page page) {
        this.pageList = page.getBody().getDataList();
        if(rootView==null){
            if(page.getTitle()!=null){
                titleRender = new ElementRender(context);
                titleRender.render(container,page.getTitle());
            }
            rootView = View.inflate(context, R.layout.elf_page_content_page_group_top1,null);
            container.addView(rootView);
        }
        try {
            renderViews();
        } catch (Exception e) {
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    private void renderViews() throws Exception{
        if(llPageGroup == null){
            llPageGroup = rootView.findViewById(R.id.llPageGroup);
            mViewPager = rootView.findViewById(R.id.view_pager);

            List<Fragment> fragments=new ArrayList<>();
            for(int i=0;i<pageList.size();i++){
                Fragment f = ElfProxy.getInstance().getHook().getFragment(context,pageList.get(i).getCode());
                fragments.add(f);
            }
            myPagerAdapter = new MyFragmentPagerAdapter(rootFragment.getChildFragmentManager(),fragments);
            mViewPager.setAdapter(myPagerAdapter);
            myPagerAdapter.notifyDataSetChanged();

            initMagicIndicator4();
        }
    }
    private void initMagicIndicator4() {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator1);
        CommonNavigator commonNavigator = new CommonNavigator(rootFragment.getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return pageList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(context.getResources().getColor(R.color.elf_text_black));
                simplePagerTitleView.setSelectedColor(context.getResources().getColor(R.color.elf_text_red));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setText(pageList.get(index).getName());
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(DisplayUtils.dp2px(context, 20));
                linePagerIndicator.setColors(context.getResources().getColor(R.color.elf_text_red));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(context, 15);
            }
        });

        fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }
    @Override
    public void transferPage(String pageCode) {
        int position = 0;
        for(int i=0;i<pageList.size();++i){
            if(pageList.get(i).getCode().equals(pageCode)){
                position = i;
                break;
            }
        }
        mViewPager.setCurrentItem(position);
    }
}

package com.zzy.elf_template.template.engine.special;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.core.model.Item;
import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.inner.MyPagerAdapter;
import com.zzy.elf_template.R;
import com.zzy.elf_template.template.WidgetHelper;
import com.zzy.elf_template.template.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/2/11
 *
 */
public class Engine3 extends Engine {
    public static final String TAG = "Engine3";
    private Context context;
    private int templateId,layoutId;
    private ViewPager viewPager;
    private List<View> viewList;
    private LinearLayout llContainer;

    /*****************************************************************************************************/
    private Engine3(Context context, int templateId, int layoutId) {
        this.context = context;
        this.templateId = templateId;
        this.layoutId = layoutId;
    }

    public Engine3() {}

    @Override
    public int getItemViewLayoutId() {
        return layoutId;
    }

    @Override
    public boolean isForViewType(Object obj, int position) {
        if(obj instanceof Section){
            return ((Section)obj).getTemplateId()==templateId;
        }else{
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, Object obj, int position) {
        ViewGroup rootView = holder.getView(R.id.elf_rootView);
        render(rootView, obj);
    }

    private Section changeW1Route(Section section){
        Section s = new Section();
        for(int i=0;i<section.getItemList().size();i++){
            Item item = section.getItemList().get(i);
            String route = "[]";
            for(int j=0;j<item.getWidgetList().size();j++){
                if(item.getWidgetList().get(j).getId().equals("w0")){
                    route = item.getWidgetList().get(j).getRoute();
                    break;
                }
            }
            for(int j=0;j<item.getWidgetList().size();j++){
                if(item.getWidgetList().get(j).getId().equals("w1")){
                    item.getWidgetList().get(j).setRoute(route);
                    break;
                }
            }
        }
        return section;
    }
    @Override
    public void render(final ViewGroup rootView,final Object obj){
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    Section section = (Section) obj;
                    Section cloneS = section.cloneMe(section);
                    changeW1Route(cloneS);
                    viewPager = rootView.findViewById(R.id.viewPager);
                    viewList = new ArrayList<>();
                    for(int i=0;i<cloneS.getItemList().size();i++){
                        View paperView = LayoutInflater.from(context).inflate(R.layout.elf_template3_item, null);
                        List<Widget> widgets = cloneS.getItemList().get(i).getWidgetList();
                        WidgetHelper.fillWidgets(context,paperView,widgets);
                        viewList.add(paperView);
                    }
                    viewPager.setAdapter(new MyPagerAdapter(viewList));
                    viewPager.setCurrentItem(0);
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            switchIndictor(position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    llContainer = rootView.findViewById(R.id.llContainer);
                    llContainer.removeAllViews();
                    for(int i=0;i<cloneS.getItemList().size();++i){
                        final View view = LayoutInflater.from(context).inflate(R.layout.elf_template3_indicator, null);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(AutoUtils.getPercentHeightSize(24),
                                AutoUtils.getPercentHeightSize(5));
                        lp.setMargins(AutoUtils.getPercentHeightSize(6),0,0,0);
                        view.setLayoutParams(lp);
                        llContainer.addView(view);
                    }
                    if(cloneS.getItemList().size()>1){
                        llContainer.setVisibility(View.VISIBLE);
                        switchIndictor(0);
                    }
                }catch(Exception e){
                    MyExceptionHandler.handle(context,TAG,e);
                }
            }
        },1);
    }

    private void switchIndictor(int position) {
        for(int i=0;i<llContainer.getChildCount();++i){
            if(i==position){
                llContainer.getChildAt(i).setSelected(true);
            }else{
                llContainer.getChildAt(i).setSelected(false);
            }
        }
    }

    @Override
    public Engine newInstance(Context context, int templateId, int layoutId, Object... args) {
        return new Engine3(context,templateId,layoutId);
    }
}

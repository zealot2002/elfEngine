package com.zzy.elf_template.template.engine.common;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzy.core.model.Item;
import com.zzy.core.model.Section;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.core.view.inner.recycleAdapter.ViewHolder;
import com.zzy.elf_template.ElfTemplateProxy;
import com.zzy.elf_template.R;
import com.zzy.elf_template.template.engine.Engine;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author zzy
 * @date 2018/2/11
 */

public class BannerEngine extends Engine {
    public static final String TAG = "BannerEngine";
    private Context context;
    private int layoutId,templateId;
    private BGABanner banner;
/*****************************************************************************************************/
    private BannerEngine(Context context, int templateId, int layoutId) {
        this.context = context;
        this.templateId = templateId;
        this.layoutId = layoutId;
    }

    public BannerEngine() {}
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

    @Override
    public void render(ViewGroup rootView,Object obj) {
        try{
            final Section section = (Section) obj;
            banner = rootView.findViewById(R.id.banner);

            banner.setData(R.layout.elf_banner_item, section.getItemList(), null);

            banner.setDelegate(new BGABanner.Delegate<ImageView, Item>() {
                @Override
                public void onBannerItemClick(BGABanner banner, ImageView itemView, Item item, int position) {
                    for(Widget w:item.getWidgetList()){
                        //list item的route都必须配置在w0上
                        if(w.getId().equals("w0")){
                            if(w.getRoute()!=null
                                    &&!w.getRoute().equals("[]")
                                    &&!w.getRoute().equals("[\"\"]")){
                                try {
                                    ElfTemplateProxy.getInstance().getHook().onClickedEvent(context,w.getRoute(),w.getStatisInfo().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(context,"err:"+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            });
            banner.setAdapter(new BGABanner.Adapter<ImageView, Item>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, Item item, int position) {
                    if(!item.getWidgetList().isEmpty()){
                        for(Widget w:item.getWidgetList()){
                            //banner的图片必须配置在w1上
                            if(w.getId().equals("w1")){
                                ElfTemplateProxy.getInstance().getHook().onSetResource(context, Uri.parse(w.getImageUri()),itemView);
                            }
                        }
                    }
                }
            });
            if(section.getItemList().size()<2){
                banner.setAutoPlayAble(false);
            }else{
                banner.setAutoPlayAble(true);
            }
        }catch(Exception e){
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    @Override
    public Engine newInstance(Context context, int templateId, int layoutId, Object... args) {
        return new BannerEngine(context,templateId,layoutId);
    }
}

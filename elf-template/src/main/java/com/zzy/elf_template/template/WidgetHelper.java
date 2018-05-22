package com.zzy.elf_template.template;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.core.model.Widget;
import com.zzy.core.utils.L;
import com.zzy.core.utils.MyExceptionHandler;
import com.zzy.elf_template.ElfTemplateProxy;
import com.zzy.elf_template.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @date 2018/2/26
 */

public class WidgetHelper {
    public static final String TAG = "WidgetHelper";
    public static final HashMap<String,Integer> widgetMap = new HashMap<String,Integer>() {{
        put("w0", R.id.w0);
        put("w1", R.id.w1);
        put("w2", R.id.w2);
        put("w3", R.id.w3);
        put("w4", R.id.w4);
        put("w5", R.id.w5);
        put("w6", R.id.w6);
        put("w7", R.id.w7);
        put("w8", R.id.w8);
        put("w9", R.id.w9);
        put("w10", R.id.w10);
    }};
/**************************************************************************************************************/
    public static void fillWidgets(final Context context, final View rootView, final List<Widget> widgets){
        try{
            hideAllWidgets(rootView);
            for(final Widget w:widgets){
                int viewId = widgetMap.get(w.getId());
                View view = rootView.findViewById(viewId);
                fillWidget(context,view,w);
            }
        }catch(Exception e){
            MyExceptionHandler.handle(context,TAG,e);
        }
    }

    public static void fillWidget(final Context context, View view, final Widget w) {
        if(view == null){
                    /*接口错误：模板没有此id，此处不作处理*/
            Log.e("zzy","接口错误：模板没有此id，此处不作处理 w.getId():"+w.getId());
            return;
        }
        if(w.getVisible()!=null&&w.getVisible().equals("0")){
            view.setVisibility(View.GONE);
            return;
        }else{
            view.setVisibility(View.VISIBLE);
        }
        try{
            if(view instanceof TextView){
                ((TextView) view).setText(w.getText());
                if(w.getTextColor()!=null
                        &&!w.getTextColor().isEmpty()){
                    ((TextView) view).setTextColor(Color.parseColor(w.getTextColor()));
                }
                if(w.getBorder()!=null
                        &&!w.getBorder().isEmpty()){
                    if(w.getBorder().equals("1")){
                        int padding = AutoUtils.getPercentHeightSize(6);
                        view.setPadding(padding,padding,padding,padding);
                        view.setBackgroundResource(R.drawable.elf_tv_bordor_1);
                    }else if(w.getBorder().equals("2")){
                        int padding = AutoUtils.getPercentHeightSize(8);
                        view.setPadding(padding*3,padding,padding*3,padding);
                        view.setBackgroundResource(R.drawable.elf_tv_bordor_2);
                    }else if(w.getBorder().equals("3")){
                        int padding = AutoUtils.getPercentHeightSize(8);
                        view.setPadding(padding*3,padding,padding*3,padding);
                        view.setBackgroundResource(R.drawable.elf_tv_bordor_3);
                    }
                }
            }else{
                if(w.getImageUri()!=null&&!w.getImageUri().isEmpty()){
                    L.e(TAG," uri:"+w.getImageUri());
                    ElfTemplateProxy.getInstance().getHook().onSetResource(context,Uri.parse(w.getImageUri()),view);
                }
            }
        }catch(Exception e){
            MyExceptionHandler.handle(context,TAG,e);
        }
        /*set view clickable*/
        if(w.getRoute()!=null
                &&!w.getRoute().equals("[]")
                &&!w.getRoute().equals("[\"\"]")){
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ElfTemplateProxy.getInstance().getHook().onClickedEvent(context,w.getRoute(),w.getStatisInfo().toString());
                    } catch (Exception e) {
                        MyExceptionHandler.handle(context,TAG,e);
                    }
                }
            });
        }
    }

    public static void hideAllWidgets(final View rootView){
        Iterator it = widgetMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            final View view = rootView.findViewById((Integer) entry.getValue());
            if(view!=null){
                view.setVisibility(View.GONE);
            }
        }
    }
}

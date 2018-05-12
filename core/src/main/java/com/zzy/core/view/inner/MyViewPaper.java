package com.zzy.core.view.inner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author zzy
 * @date 2018/2/22
 */

public class MyViewPaper extends ViewPager {
    public MyViewPaper(@NonNull Context context) {
        super(context);
    }

    public MyViewPaper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
        this.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}

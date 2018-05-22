package com.zzy.elfengine;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.core.utils.L;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    ImageView imageView;
    FrameLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}

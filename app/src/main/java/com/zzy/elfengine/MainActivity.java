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

        root = findViewById(R.id.root);
        imageView = findViewById(R.id.imageView);
        Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" +R.mipmap.ic_launcher);
//        Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" +R.drawable.home_tab1_selector);
//        Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" +R.mipmap.ic_launcher);

//        ImageLoaderUtils.getInstance().showImg(this,uri,imageView);

//        String resId = uri.getLastPathSegment();
//        root.setBackgroundResource(R.color.colorAccent);

    }
}

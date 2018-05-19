package com.zzy.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzy.common.elf.ElfConstants;
import com.zzy.commonlib.base.BaseActivity;
import com.zzy.core.ElfProxy;
import com.zzy.home.pageAdapter.FoundAdapter;
import com.zzy.home.pageAdapter.HomeAdapter;
import com.zzy.home.pageAdapter.MineAdapter;
import com.zzy.home.pageAdapter.ServiceAdapter;


/**
 * @author zzy
 * @Description:
 * @date 2018/04/26 14:50:36
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener{
    private Fragment[] fragments;
    private LinearLayout mLlAllTab;
    private LinearLayout[] mTabs;
    private int currentTabIndex;

/****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_home);
        currentTabIndex = 0;
        initTabButton();
        initFragment();
        showFragment(0);
//        presenter = new HomeActivityPresenter(this);

    }
    private void initTabButton() {
        mLlAllTab = findViewById(R.id.llAllTab);
        mTabs = new LinearLayout[4];
        mTabs[0] = findViewById(R.id.llTab0);
        mTabs[1] = findViewById(R.id.llTab1);
        mTabs[2] = findViewById(R.id.llTab2);
        mTabs[3] = findViewById(R.id.llTab3);
        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        mTabs[2].setOnClickListener(this);
        mTabs[3].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }
    private void initFragment() {
        fragments = new Fragment[]{
                getHomeFragment(),
                getServiceFragment(),
                getFoundFragment(),
                getMineFragment()
        };
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0]).show(fragments[0]).commitAllowingStateLoss();
    }
    private void showFragment(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;

        changeTabColor(currentTabIndex);
    }
    private void changeTabColor(int index) {
        for (int i = 0; i < mLlAllTab.getChildCount(); i++) {
            LinearLayout currentView = (LinearLayout) mLlAllTab.getChildAt(i);
            ImageView currentImage = (ImageView) currentView.getChildAt(0);
            TextView current_TextView = (TextView) currentView.getChildAt(1);
            switch (i) {
                case 0:
                    if (index == i) {
                        currentImage.setImageResource(R.mipmap.home_tab_home_checked);
                    } else {
                        currentImage.setImageResource(R.mipmap.home_tab_home_default);
                    }
                    break;

                case 1:
                    if (index == i) {
                        currentImage.setImageResource(R.mipmap.home_tab_service_checked);
                    } else {
                        currentImage.setImageResource(R.mipmap.home_tab_service_default);
                    }
                    break;

                case 2:
                    if (index == i) {
                        currentImage.setImageResource(R.mipmap.home_tab_found_checked);
                    } else {
                        currentImage.setImageResource(R.mipmap.home_tab_found_default);
                    }
                    break;

                case 3:
                    if (index == i) {
                        currentImage.setImageResource(R.mipmap.home_tab_mine_checked);
                    } else {
                        currentImage.setImageResource(R.mipmap.home_tab_mine_default);
                    }
                    break;
                default:
                    break;
            }

            if (i == index) {
                current_TextView.setTextColor(this.getResources().getColor(R.color.home_main_tab_text_color_select));
            } else {
                current_TextView.setTextColor(this.getResources().getColor(R.color.home_main_tab_text_color_unselect));
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llTab0) {
            showFragment(0);
        }else if (view.getId() == R.id.llTab1) {
            showFragment(1);
        }else if (view.getId() == R.id.llTab2) {
            showFragment(2);
        }else if (view.getId() == R.id.llTab3) {
            showFragment(3);
        }
    }

    private Fragment getHomeFragment() {
//        return new Fragment();
        return ElfProxy.getInstance().makeNormalPage(ElfConstants.ELF_PAGE_HOME,new HomeAdapter());
    }
    private Fragment getServiceFragment() {
//        return new Fragment();
        return ElfProxy.getInstance().makeRefreshPage(ElfConstants.ELF_PAGE_SERVICE,new ServiceAdapter());
    }
    private Fragment getFoundFragment() {
//        return new Fragment();
        return ElfProxy.getInstance().makeNormalPage(ElfConstants.ELF_PAGE_FOUND,new FoundAdapter());
    }
    private Fragment getMineFragment() {
//        return new Fragment();
        return ElfProxy.getInstance().makeRefreshPage(ElfConstants.ELF_PAGE_MINE,new MineAdapter());
    }
}
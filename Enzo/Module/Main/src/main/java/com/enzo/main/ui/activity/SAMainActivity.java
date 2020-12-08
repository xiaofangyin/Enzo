package com.enzo.main.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.Gravity;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PermissionsUtils;
import com.enzo.commonlib.utils.statusbar.stateappbar.bar.StateAppBar;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.tablayout.TabLayout;
import com.enzo.commonlib.widget.tablayout.TabView;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.flkit.router.ModuleBRouterPath;
import com.enzo.flkit.router.ModuleCRouterPath;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.main.R;
import com.enzo.main.config.TabEntityConfig;
import com.enzo.main.plugin.SAPluginManager;
import com.enzo.skin.manager.loader.SkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SAMainActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAMainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private TabLayout mTabLayout;
    private List<Fragment> mFragments;
    private Fragment mCurrentPrimaryFragment;

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
        StateAppBar.setStatusBarColor(this, SkinManager.getInstance().getColor(R.color.color_major_c1));

        drawerLayout = findViewById(R.id.home_drawer_layout);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mFragments = getFragments();
        switchFragment(0);

        mTabLayout.initData(TabEntityConfig.getEntities(this));
        mTabLayout.setCurrentItem(0);
        mTabLayout.showRedPoint(1);
        mHandler.sendEmptyMessage(0);

        if (!PermissionsUtils.areNotificationsEnabled(this)) {
            PermissionsUtils.showEnableNotificationsDialog(this);
        }

        //闲时加载
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                LogUtil.d("IdleHandler queueIdle...");
                return false;
            }
        });
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mTabLayout.getMessageNum(2) >= 99) {
                mTabLayout.resetMessageNum(2);
            }
            mTabLayout.addMessageNum(2, 1);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    @Override
    public void initListener() {
        mTabLayout.setOnTabClickListener(new TabLayout.OnTabClickListener() {
            @Override
            public void onTabClick(TabView view, int position) {
                switchFragment(position);
            }

            @Override
            public void onTabReClick(TabView view, int position) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("on new intent...");
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add((Fragment) ARouter.getInstance().build(ModuleARouterPath.MODULE_A_FRAGMENT).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(ModuleBRouterPath.MODULE_B_FRAGMENT).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(ModuleCRouterPath.MODULE_C_FRAGMENT).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(ModuleDRouterPath.MODULE_D_FRAGMENT).navigation());
//        for (FLPluginBaseManagerInterface manager : SAPluginManager.getInstance().getFactoryList()) {
//            Fragment fragment = manager.buildHomeTabFragment();
//            if (fragment != null) {
//                fragments.add(manager.buildHomeTabFragment());
//            }
//        }
        return fragments;
    }

    private void switchFragment(int index) {
        if (mFragments == null || mFragments.isEmpty()) return;

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        showFragment(transaction, mFragments.get(index));
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();

        if (index == 0) {
            //打开手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            //禁止手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mCurrentPrimaryFragment != null && mCurrentPrimaryFragment.isAdded()) {
            transaction.hide(mCurrentPrimaryFragment);
            transaction.setMaxLifecycle(mCurrentPrimaryFragment, Lifecycle.State.STARTED);
        }
    }

    private void showFragment(FragmentTransaction transaction, Fragment fragment) {
        if (fragment != null && fragment != mCurrentPrimaryFragment) {
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(R.id.main_content, fragment, fragment.getClass().getSimpleName());
            }
            transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            mCurrentPrimaryFragment = fragment;
        }
    }

    public void openDrawer(int gravity) {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(gravity);
        }
    }

    public void closeDrawer(int gravity) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(gravity);
        }
    }

    private long firstTime = 0; //点击两次退出应用计时

    @SuppressLint("WrongConstant")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.START)) {
                closeDrawer(Gravity.START);
                return true;
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    ToastUtil.show("再按一次退出程序");
                    firstTime = System.currentTimeMillis();
                    return true;
                } else {
                    ActivityHelper.getManager().appExit();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        List<FLPluginInterface> factoryList = SAPluginManager.getInstance().getPluginList();
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).onReleaseResources();
        }
    }
}

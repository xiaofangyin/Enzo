package com.enzo.main.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.enzo.commonlib.utils.statusbar.bar.StateAppBar;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
import com.enzo.commonlib.utils.taskqueue.TaskPriority;
import com.enzo.commonlib.utils.taskqueue.TaskScheduler;
import com.enzo.commonlib.utils.taskqueue.task.Task1;
import com.enzo.commonlib.utils.taskqueue.task.Task2;
import com.enzo.commonlib.utils.taskqueue.task.Task3;
import com.enzo.commonlib.utils.taskqueue.task.Task4;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.tablayout.TabLayout;
import com.enzo.commonlib.widget.tablayout.TabView;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.flkit.router.ModuleBRouterPath;
import com.enzo.flkit.router.ModuleCRouterPath;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.main.R;
import com.enzo.main.config.TabEntityConfig;
import com.enzo.main.plugin.SAHostDelegateManager;

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

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
        StatusBarUtils.StatusBarLightMode(this);

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

        LogUtil.d(PhoneUtils.getInstance().getUniqueId(this));


        Task1 task1 = new Task1();
        task1.setPriority(TaskPriority.LOW);
        task1.setDuration(5000);

        Task2 task2 = new Task2();
        task2.setPriority(TaskPriority.HIGH);

        Task3 task3 = new Task3();
        task3.setPriority(TaskPriority.DEFAULT);
        task3.setDuration(3000);

        Task4 task4 = new Task4();
        task4.setPriority(TaskPriority.HIGH);
        task4.setDuration(6000);

        TaskScheduler.getInstance().enqueue(task1);
        TaskScheduler.getInstance().enqueue(task2);
        TaskScheduler.getInstance().enqueue(task3);
        TaskScheduler.getInstance().enqueue(task4);
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
        fragments.add((Fragment) ARouter.getInstance().build(ModuleARouterPath.MODULE_A_FRAGMENT2).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(ModuleBRouterPath.MODULE_B_FRAGMENT).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(ModuleCRouterPath.MODULE_C_FRAGMENT).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(ModuleDRouterPath.MODULE_D_FRAGMENT).navigation());
        return fragments;
    }

    private void switchFragment(int index) {
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

    private void showFragment(FragmentTransaction transaction, Fragment fragment) {
        if (fragment != null) {
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(R.id.main_content, fragment, fragment.getClass().getSimpleName());
            }
        }
    }

    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; i < mFragments.size(); i++) {
            if (mFragments.get(i) != null && mFragments.get(i).isAdded()) {
                transaction.hide(mFragments.get(i));
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SAHostDelegateManager.getInstance().releaseResources();
    }
}

package com.ifenglian.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ifenglian.commonlib.utils.common.FLLog;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 文 件 名: BaseActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    public static String TAG = BaseActivity.class.getSimpleName();
    private CompositeSubscription sCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initSubscription();
        initView();
        initData();
        initListener();
    }

    private void initSubscription() {
        if (sCompositeSubscription == null || sCompositeSubscription.isUnsubscribed()) {
            sCompositeSubscription = new CompositeSubscription();
        }
    }

    /**
     * 添加Subscription
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        FLLog.d("add subscription");
        sCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sCompositeSubscription != null) {
            FLLog.d("base activity unSubscribe");
            sCompositeSubscription.unsubscribe();
        }
    }
}

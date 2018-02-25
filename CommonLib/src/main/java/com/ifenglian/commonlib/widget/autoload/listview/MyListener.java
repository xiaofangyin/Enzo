package com.ifenglian.commonlib.widget.autoload.listview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MyListener implements PullToRefreshLayout.OnRefreshListener {

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 5000);
    }
}

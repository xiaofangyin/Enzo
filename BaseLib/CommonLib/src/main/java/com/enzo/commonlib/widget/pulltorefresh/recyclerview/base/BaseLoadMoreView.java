package com.enzo.commonlib.widget.pulltorefresh.recyclerview.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;

/**
 * 文 件 名: BaseLoadMoreView 加载更多基类，如果要自定义加载布局只需要继承该基类，在对应的方法中进行逻辑整理
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BaseLoadMoreView extends LinearLayout {

    //正在加载
    public final static int STATE_LOADING = 0;
    //加载完成
    public final static int STATE_SUCCESS = 1;
    //加载失败
    public final static int STATE_FAILED = 2;
    //没有数据
    public final static int STATE_NO_DATA = 3;
    //初始化状态
    public int mState = STATE_SUCCESS;

    public View mContainer;
    public PullToRefreshRecyclerView.OnLoadListener mRetryListener;

    public BaseLoadMoreView(Context context) {
        super(context);
        LayoutParams lp = new LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
        initView(context);
    }

    /**
     * 状态设置
     */
    public abstract void initView(Context context);

    /**
     * 状态设置
     */
    public abstract void setState(int state);

    /**
     * 销毁页面对象和动画，防止内存泄漏
     */
    public abstract void destroy();

    /**
     * 获取状态
     */
    public int getState() {
        return mState;
    }

    public void setOnRetryListener(PullToRefreshRecyclerView.OnLoadListener listener){
        mRetryListener = listener;
    }
}

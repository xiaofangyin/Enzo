package com.ifenglian.module_d.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.net.retrofit.Fault;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDMovieAdapter;
import com.ifenglian.module_d.bean.Movie;
import com.ifenglian.module_d.loader.MDMovieLoader;

import java.util.List;

import rx.functions.Action1;

/**
 * 文 件 名: MDMovieActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/24
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDMovieActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MDMovieAdapter mMovieAdapter;
    private MDMovieLoader mMovieLoader;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_retrofit;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new MovieDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mMovieAdapter = new MDMovieAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void initData() {
        mMovieLoader = new MDMovieLoader();
        getMovieList();
    }

    @Override
    public void initListener() {

    }

    /**
     * 获取电影列表
     */
    private void getMovieList() {
        mMovieLoader.getMovie(0, 10).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                mMovieAdapter.setMovies(movies);
                mMovieAdapter.notifyDataSetChanged();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG", "error message:" + throwable.getMessage());
                if (throwable instanceof Fault) {
                    Fault fault = (Fault) throwable;
                    if (fault.getErrorCode() == 404) {
                        //错误处理
                    } else if (fault.getErrorCode() == 500) {
                        //错误处理
                    } else if (fault.getErrorCode() == 501) {
                        //错误处理
                    }
                }
            }
        });

    }

    public static class MovieDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 20);
        }
    }
}

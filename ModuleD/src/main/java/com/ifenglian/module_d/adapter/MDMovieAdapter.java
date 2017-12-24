package com.ifenglian.module_d.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenglian.module_d.R;
import com.ifenglian.module_d.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 文 件 名: MDMovieAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/24
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDMovieAdapter extends RecyclerView.Adapter {

    private List<Movie> mMovies;

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.md_item_moive, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;
        movieHolder.time.setText("上映时间：" + movie.year + "年");
        movieHolder.title.setText(movie.title);
        movieHolder.subTitle.setText(movie.original_title);
        Picasso.with(holder.itemView.getContext()).load(movie.images.small).into(movieHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView title;
        public TextView subTitle;
        public TextView time;

        public MovieHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_image);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            subTitle = (TextView) itemView.findViewById(R.id.movie_sub_title);
            time = (TextView) itemView.findViewById(R.id.movie_time);
        }
    }
}

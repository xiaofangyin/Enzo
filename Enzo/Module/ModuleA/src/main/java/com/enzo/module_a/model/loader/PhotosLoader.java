package com.enzo.module_a.model.loader;

import com.enzo.commonlib.net.retrofit.BaseLoader;
import com.enzo.commonlib.net.retrofit.RetrofitServiceManager;
import com.enzo.module_a.model.bean.MAHomeGoodsBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 文 件 名: PhotosLoader
 * 创 建 人: xiaofy
 * 创建日期: 2020/11/15
 * 邮   箱: xiaofywork@163.com
 */
public class PhotosLoader extends BaseLoader {

    IGetPhotosLoader getPhotosLoader;

    public PhotosLoader() {
        getPhotosLoader = RetrofitServiceManager.getInstance().create(IGetPhotosLoader.class);
    }

    public Observable<List<MAHomeGoodsBean>> getPhotos(int page, int limit) {
        return observe(getPhotosLoader.getPhotos(page, limit));
    }

    public interface IGetPhotosLoader {

        @GET("v2/list")
        Observable<List<MAHomeGoodsBean>> getPhotos(@Query("page") int page, @Query("limit") int limit);
    }
}

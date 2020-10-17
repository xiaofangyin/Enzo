package com.enzo.module_a.model.exetutor;

import com.enzo.commonlib.net.okhttp.BaseExecutor;
import com.enzo.commonlib.net.okhttp.OkHttpCallBack;
import com.enzo.commonlib.utils.common.GsonHelper;
import com.enzo.module_a.model.bean.MAHomeGoodsBean;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 文 件 名: MAPhotoListExecutor
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/13
 * 邮   箱: xiaofywork@163.com
 * https://picsum.photos/v2/list
 */
public class MAPhotoListExecutor extends BaseExecutor {

    @Override
    protected String getHost() {
        return "https://picsum.photos";
    }

    @Override
    protected String getVirtual() {
        return "/v2/list";
    }

    @Override
    public void execute() {
        get(new OkHttpCallBack() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                if (jsonCallback != null) {
                    List<MAHomeGoodsBean> list = GsonHelper.toList(s, MAHomeGoodsBean.class);
                    jsonCallback.onSuccess(list);
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                if (jsonCallback != null) {
                    jsonCallback.onFailed(e);
                }
            }
        });
    }
}

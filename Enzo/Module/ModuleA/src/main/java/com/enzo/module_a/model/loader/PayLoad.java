package com.enzo.module_a.model.loader;

import com.enzo.commonlib.net.retrofit.BaseResponse;
import com.enzo.commonlib.net.retrofit.Fault;

import rx.functions.Func1;

public class PayLoad<T> implements Func1<BaseResponse<T>, T> {

    @Override
    public T call(BaseResponse<T> tBaseResponse) {//获取数据失败时，包装一个Fault 抛给上层处理错误
        if (!tBaseResponse.isSuccess()) {
            if (tBaseResponse.getStatus() == 1007) {
//                MAPluginManagerImpl.getInstance().hostDelegate.logout();
            }
            throw new Fault(tBaseResponse.getStatus(), tBaseResponse.getMsg());
        }
        return tBaseResponse.getData();
    }
}

package org.request.http.request.call;

import org.request.http.HttpRequestCallBack;
import org.request.http.request.response.Response;

public interface Call {
    /**
     * 同步执行
     */
    Response execute();

    /**
     * 异步执行
     */
    void enqueue(HttpRequestCallBack callBack);
}

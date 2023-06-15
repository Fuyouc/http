package org.request.http.request;

import org.request.http.config.NetWorkConfiguration;
import org.request.http.request.Request;
import org.request.http.request.response.Response;

public interface NetworkRequest {
    /**
     * 发送网络请求
     */
    Response request(Request request);
}

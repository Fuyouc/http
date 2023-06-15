package org.request.retrofit.response;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Response<T> {
    /**
     * 获取数据
     */
    T getData();

    /**
     * 获取请求方式
     */
    String getMethod();

    /**
     * 获取状态码
     */
    int getCode();

    /**
     * 获取响应数据长度
     */
    int getContentLength();

    /**
     * 获得URL
     */
    String getURL();

    /**
     * 获取单个响应头
     */
    String getHeader(String key);

    List<String> getHeaders(String key);

    Map<String, List<String>> getHeaders();

    InputStream getInputStream();
}

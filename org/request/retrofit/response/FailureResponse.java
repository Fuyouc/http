package org.request.retrofit.response;

import java.util.List;
import java.util.Map;

/**
 * 失败的响应数据
 */
public interface FailureResponse {
    /**
     * 获取错误信息
     */
    String data();

    /**
     * 获取请求方式
     */
    String getRequestMethod();

    /**
     * 获取状态码
     */
    int getCode();

    /**
     * 获取响应数据长度
     */
    int getContentLength();

    /**
     * 获取响应类型
     */
    String getContentType();

    /**
     * 获取单个响应头
     */
    String getHeader(String key);

    List<String> getHeaders(String key);

    Map<String, List<String>> getHeaders();
}

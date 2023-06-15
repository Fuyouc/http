package org.request.http.request.response;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface Response {
    /**
     * 获取响应数据
     */
    String data();

    /**
     * 设置响应信息
     */
    void setData(String data);

    /**
     * 获取请求方式
     */
    String getRequestMethod();

    /**
     * 获取状态码
     */
    int getCode();

    /**
     * 设置状态码
     */
    void setCode();

    /**
     * 获取响应数据长度
     */
    int getContentLength();

    /**
     * 设置响应数据长度
     */
    void setContentLength(int length);

    /**
     * 获取响应类型
     */
    String getContentType();

    /**
     * 获取单个响应头
     */
    String getHeader(String key);

    /**
     * 设置响应头
     */
    void setHeader(String key,String value);

    List<String> getHeaders(String key);

    Map<String, List<String>> getHeaders();

    InputStream getInputStream();

    String getURL();
}

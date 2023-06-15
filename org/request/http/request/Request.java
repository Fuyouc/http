package org.request.http.request;

import org.request.http.config.NetWorkConfiguration;

import java.util.Map;

public interface Request {

    /**
     * 添加参数（如果有一个Key对应多个Value，会自动转换成List类型）
     */
    void addParam(String key,Object value);

    void setParam(Map<String,Object> map);

    /**
     * 添加请求头
     */
    void addHeader(String key,String value);

    /**
     * 设置请求头
     */
    void setURL(String url);

    /**
     * 设置请求方式
     */
    void setMethod(String method);

    /**
     * 设置POST类型
     */
    void setContentType(MediaType contentType);

    void setConfiguration(NetWorkConfiguration configuration);

    /**
     * 获取指定参数
     */
    Object getParam(String key);
    /**
     * 获取参数列表
     */
    Map<String,Object> getParams();
    /**
     * 获取指定请求头
     */
    String getHeader(String key);
    /**
     * 获取请求头列表
     */
    Map<String,String> getHeader();
    /**
     * 获取访问URL
     */
    String getURL();
    /**
     * 获取请求方式
     */
    String getMethod();

    /**
     * 获取网络配置
     */
    NetWorkConfiguration getConfiguration();

    /**
     * 获取POST类型
     */
    MediaType getContentType();
}

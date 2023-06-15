package org.request.retrofit.response;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class XResponse<T> implements Response<T>{

    private T data;
    private org.request.http.request.response.Response response;

    public XResponse(T data, org.request.http.request.response.Response response) {
        this.data = data;
        this.response = response;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getMethod() {
        return response.getRequestMethod();
    }

    @Override
    public int getCode() {
        return response.getCode();
    }

    @Override
    public int getContentLength() {
        return response.getContentLength();
    }

    @Override
    public String getHeader(String key) {
        return response.getHeader(key);
    }

    @Override
    public List<String> getHeaders(String key) {
        return response.getHeaders(key);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return response.getHeaders();
    }

    @Override
    public InputStream getInputStream() {
        return response.getInputStream();
    }

    @Override
    public String toString() {
        return "XResponse{" +
                "data=" + data +
                ", response=" + response +
                '}';
    }
}

package org.request.http.request.response;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestResponse implements Response{

    private String data;
    private String method;
    private int code;
    private int contentLength;
    private String contentType;
    private Map<String,List<String>> headers;
    private InputStream inputStream;

    private String URL;


    public RequestResponse() {
    }

    public RequestResponse(String data) {
        this.data = data;
    }

    public RequestResponse(String data, int code, String method, int contentLength, String contentType, Map<String, List<String>> headers, InputStream inputStream,String URL) {
        this.data = data;
        this.code = code;
        this.method = method;
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.headers = headers;
        this.inputStream = inputStream;
        this.URL = URL;
    }

    @Override
    public String data() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getRequestMethod() {
        return method;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode() {
        this.code = code;
    }

    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentLength(int length) {
        this.contentLength = length;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getHeader(String key) {
        List<String> list = headers.get(key);
        return list == null ? null : list.get(0);
    }

    @Override
    public void setHeader(String key, String value) {
        if (headers.containsKey(key)){
            headers.get(key).add(value);
        }else {
            List<String> list = new ArrayList<>();
            list.add(value);
            headers.put(key,list);
        }
    }

    @Override
    public List<String> getHeaders(String key) {
        return headers.get(key);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String getURL() {
        return this.getURL();
    }

    @Override
    public String toString() {
        return "RequestResponse{" +
                "data='" + data + '\'' +
                ", method='" + method + '\'' +
                ", code=" + code +
                ", contentLength=" + contentLength +
                ", contentType='" + contentType + '\'' +
                ", headers=" + headers +
                ", inputStream=" + inputStream +
                ", URL='" + URL + '\'' +
                '}';
    }
}

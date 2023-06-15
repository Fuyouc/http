package org.request.http.request;

import org.json.ValueUtils;
import org.request.http.config.NetWorkConfiguration;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.http.HttpRequest;
import java.util.*;

public final class XRequest implements Request {

    private Map<String, String> headers;
    private Map<String, Object> params;
    private NetWorkConfiguration configuration;
    private String url;
    private String method = "GET";

    private MediaType contentType = MediaType.APPLICATION_JSON;

    private XRequest() {
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    @Override
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setHeader(Map<String, String> map) {
        headers = map;
    }

    @Override
    public void addParam(String key, Object value) {
        if (params.containsKey(key)){
            Object oldValue = params.get(key);
            if (List.class.isInstance(oldValue)){
                List<Object> list = (List<Object>) oldValue;
                list.add(value);
            }else {
                List<Object> list = new ArrayList<>();
                list.add(oldValue);
                list.add(value);
                params.put(key,list);
            }
        }else {
            params.put(key,value);
        }
    }

    public void reconfigurationParam(){
        try {
            Map<String,Object> map = new HashMap<>(params);
            params.clear();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                object(key,value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void object(String key,Object value) throws Exception {
        if (value instanceof List){
            //处理List类型
            list(key,value);
        }else if (value instanceof Map){
            //处理Map类型
            map(value);
        }else if (!(value instanceof File) && !ValueUtils.isWrap(value)){
            //处理普通对象类型
            object(value);
        }else {
            //处理基本数据类型
            basic(key,value);
        }
    }

    private void map(Object value) throws Exception {
        Map<String,Object> map = (Map<String, Object>) value;
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object mapValue = entry.getValue();
            object(key,mapValue);
        }
    }

    private void object(Object value) throws Exception {
        Class<?> clazz = value.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String key = field.getName();
            Object fieldValue = field.get(value);
            object(key,fieldValue);
        }
    }

    private void list(String key,Object value) throws Exception {
        List<Object> values = (List<Object>) value;
        for (Object old : values) {
            object(key,old);
        }
    }

    private void basic(String key,Object value) throws UnsupportedEncodingException {
        if (params.containsKey(key)){
            Object oldValue = params.get(key);
            if (List.class.isInstance(oldValue)){
                List<Object> list = (List<Object>) oldValue;
                list.add(value);
            }else {
                List<Object> list = new ArrayList<>();
                list.add(oldValue);
                list.add(value);
                params.put(key,list);
            }
        }else {
            params.put(key,value);
        }
    }

    @Override
    public void setParam(Map<String, Object> map) {
        params = map;
    }

    @Override
    public void setConfiguration(NetWorkConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    @Override
    public Object getParam(String key) {
        return params.get(key);
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public Map<String, String> getHeader() {
        return headers;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public NetWorkConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public MediaType getContentType() {
        return contentType;
    }

    public static class Builder{
        private XRequest request;

        public Builder() {
            request = new XRequest();
        }

        public Builder addHeader(String key,String value){
            request.addHeader(key,value);
            return this;
        }

        public Builder setHeader(Map<String,String> map){
            request.setHeader(map);
            return this;
        }

        public Builder addParam(String key,Object value){
            request.addParam(key,value);
            return this;
        }

        public Builder setParam(Map<String,Object> map){
            request.setParam(map);
            return this;
        }

        public Builder setConfiguration(NetWorkConfiguration configuration){
            request.setConfiguration(configuration);
            return this;
        }

        public Builder setURL(String url){
            request.setURL(url);
            return this;
        }

        public Builder setReadTimeout(int timeout){
            request.getConfiguration().setReadTimeout(timeout);
            return this;
        }

        public Builder setConnectionTimeout(int timeout){
            request.getConfiguration().setConnectionTimeout(timeout);
            return this;
        }

        public Builder setUseCaches(boolean enable){
            request.getConfiguration().setUseCaches(enable);
            return this;
        }

        public Builder setContentType(MediaType contentType){
            request.setContentType(contentType);
            return this;
        }

        public Builder get(){
            request.setMethod("GET");
            return this;
        }

        public Builder post(){
            request.setMethod("POST");
            return this;
        }

        public Request build(){
            return request;
        }
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "headers=" + headers +
                ", params=" + params +
                ", configuration=" + configuration +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}

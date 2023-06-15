package org.request.http.request;

import com.alibaba.fastjson2.JSON;

public enum MediaType {
    APPLICATION_JSON("application/json"),
    APPLICATION_FROM_ENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FROM_DATA("multipart/form-data"),
    ;
    private String name;

    MediaType(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

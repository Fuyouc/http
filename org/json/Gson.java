package org.json;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public final class Gson {

    public static String jsonString(Object object){
        return JSONObject.toJSONString(object);
    }

    public static <T> T object(String json,Class<T> targetClass){
        if (json == null || targetClass == null) return null;
        try {
            if ('{' == json.charAt(0)){
                return JSONObject.parseObject(json,targetClass);
            }else if ('[' == json.charAt(0)){
                return (T) JSONArray.parseArray(json,targetClass);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

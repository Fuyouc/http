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
                JSONObject jsonObject = JSONObject.parseObject(json);
                return (T) jsonObject(jsonObject,targetClass);
            }else if ('[' == json.charAt(0)){
                JSONArray jsonArray = JSONArray.parseArray(json);
                return (T) jsonArray(jsonArray,targetClass);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static <T> Object jsonObject(JSONObject jsonObject, Class<T> targetClass) throws Exception {
        if (Map.class.isAssignableFrom(targetClass)){
            return map(jsonObject);
        }else {
            return object(targetClass,jsonObject);
        }
    }

    private static <T> Object jsonArray(JSONArray jsonArray, Class<T> targetClass) throws Exception {
        if (List.class.isAssignableFrom(targetClass)){
            if (Map.class.isAssignableFrom(targetClass)){
                return map(jsonArray);
            }else {
                return list(targetClass,jsonArray);
            }
        }
        return null;
    }

    private static Object object(Class<?> targetClass,JSONObject jsonObject) throws Exception{
        Object target = targetClass.newInstance();
        for (Field field : targetClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = jsonObject.get(field.getName());
            if (value instanceof JSONObject){
                JSONObject valueJsonObject = (JSONObject) value;
                field.set(target,object(field.getType(),valueJsonObject));
            }else if (value instanceof JSONArray){
                JSONArray valueJsonObject = (JSONArray) value;
                // 获取该字段的类型
                Type type = field.getGenericType();
                // 判断该字段是否为参数化类型（即泛型）
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 获取参数化类型中的实际类型（即 List 中的元素类型）
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    // 取出数组中第一个元素（即 List 中的元素类型）
                    Type elementType = actualTypeArguments[0];
                    field.set(target,list((Class<?>) elementType,valueJsonObject));
                }
            }else {
                if (value != null) {
                    field.set(target, ValueUtils.basicType(field.getType(), String.valueOf(value)));
                }
            }
        }
        return target;
    }
    private static Object map(Object json){
        Map<String,Object> map = new HashMap<>();
        if (json instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) json;
            Iterator<String> iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject || value instanceof JSONArray){
                    map.put(key,map(value));
                }else {
                    map.put(key,value);
                }
            }
            return map;
        }else if (json instanceof JSONArray){
            List list = new ArrayList();
            JSONArray jsonArray = (JSONArray) json;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null){
                    list.add(map(jsonObject));
                }else {
                    list.add(jsonArray.get(i));
                }
            }
            return list;
        }
        return null;
    }

    private static List list(Class<?> clazz,JSONArray jsonArray) throws Exception{
        List list = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject){
                list.add(object(clazz,(JSONObject) value));
            }else {
                list.add(value);
            }
        }
        return list;
    }
}

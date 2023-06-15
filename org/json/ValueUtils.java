package org.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ValueUtils {
    public static Object basicType(Class<?> clazz,String value){
        if (value == null) return null;
        if (isInt(clazz)){
            return Integer.valueOf(value);
        }else if (isDouble(clazz)){
            return Double.valueOf(value);
        }else if (isFloat(clazz)){
            return Float.valueOf(value);
        }else if (isBoolean(clazz)){
            return Boolean.valueOf(value);
        }
        return value;
    }

    public static boolean isInt(Class<?> clazz){
        if (clazz == null) return false;
        return int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz);
    }

    public static boolean isDouble(Class<?> clazz){
        if (clazz == null) return false;
        return double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz);
    }

    public static boolean isFloat(Class<?> clazz){
        if (clazz == null) return false;
        return float.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz);
    }

    public static boolean isBoolean(Class<?> clazz){
        if (clazz == null) return false;
        return boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz);
    }

    public static boolean isWrap(Object value){
        Class<?> clazz = value.getClass();
        try {
            return value instanceof String || (clazz.isPrimitive()) || ((Class<?>)clazz.getField("TYPE").get(null)).isPrimitive();
        }catch (Exception e){
            return false;
        }
    }

}

package org.request.retrofit.call;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import org.json.Gson;
import org.request.http.HttpRequestCallBack;
import org.request.retrofit.response.Callback;
import org.request.retrofit.response.FailureResponse;
import org.request.retrofit.response.Response;
import org.request.retrofit.response.XResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RetrofitCall<T> implements Call<T>{

    /**
     * 目标接口的泛型信息
     */
    private Type genericsType;

    /**
     * http中的call
     */
    private org.request.http.request.call.Call call;

    public RetrofitCall(Type returnType, org.request.http.request.call.Call requestCall) {
        this.genericsType = returnType;
        this.call = requestCall;
    }

    @Override
    public Response<T> execute() {
        org.request.http.request.response.Response response = call.execute();
        return handler(response);
    }

    @Override
    public void enqueue(Callback<T> callback) {
        call.enqueue(new HttpRequestCallBack() {
            @Override
            public void onFailure(FailureResponse response) {
                callback.onFailure(response);
            }

            @Override
            public void onResponse(org.request.http.request.response.Response response) {
                callback.onResponse(handler(response));
            }
        });
    }

    private Response<T> handler(org.request.http.request.response.Response response){
        if (genericsType instanceof Class && String.class.isAssignableFrom((Class<?>) genericsType)){
            //如果是String类型，则将原始字符串返回
            return new XResponse<T>((T) response.data(),response);
        }
        //利用fastJson完成Json解析操作
        Object result = JSONObject.parseObject(response.data(),genericsType);
        return new XResponse<T>((T) result,response);
    }

}

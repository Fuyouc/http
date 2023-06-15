package org.request.retrofit.call;

import org.json.Gson;
import org.request.http.HttpRequestCallBack;
import org.request.retrofit.response.Callback;
import org.request.retrofit.response.FailureResponse;
import org.request.retrofit.response.Response;
import org.request.retrofit.response.XResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class RetrofitCall<T> implements Call<T>{

    /**
     * 目标接口的泛型信息
     */
    private Type returnType;

    /**
     * http中的call
     */
    private org.request.http.request.call.Call call;

    public RetrofitCall(Type returnType, org.request.http.request.call.Call requestCall) {
        this.returnType = returnType;
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
        T result = null;
        Class<?> returnClass = null;

        if (returnType instanceof Class){
            //如果没有泛型
            returnClass = (Class<?>) returnType;
        }else if (returnType instanceof ParameterizedType){
            //有泛型
            ParameterizedType type = (ParameterizedType) returnType;
            returnClass = (Class<?>) type.getRawType();
        }
        if (String.class.isAssignableFrom(returnClass)){
            //如果是string类型，则直接将响应结果给他就好
            return new XResponse<T>((T) response.data(),response);
        } else if (!(Void.class.isAssignableFrom(returnClass)) &&response.data() != null) {
            result = (T) Gson.object(response.data(), returnClass);
        }
        return new XResponse<>(result,response);
    }

}

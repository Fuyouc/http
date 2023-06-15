package org.request.retrofit.call;

import org.request.retrofit.response.Callback;
import org.request.retrofit.response.Response;

public interface Call<T> {

    Response<T> execute();

    void enqueue(Callback<T> callback);
}

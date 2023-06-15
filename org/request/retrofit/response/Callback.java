package org.request.retrofit.response;

import org.request.retrofit.call.Call;

public interface Callback<T> {
    void onFailure(FailureResponse response);

    void onResponse(Response<T> response);
}

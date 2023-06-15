package org.request.http;

import org.request.http.request.response.Response;
import org.request.retrofit.response.FailureResponse;

public interface HttpRequestCallBack {
    void onFailure(FailureResponse failureResponse);
    void onResponse(Response response);
}

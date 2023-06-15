package org.request.http.request.interceptor;

import org.request.http.request.Request;
import org.request.http.request.response.Response;

/**
 * Http拦截器
 */
public interface HttpInterceptor {
    Request requestInterceptor(Request request);
    Response responseInterceptor(Response response);
}

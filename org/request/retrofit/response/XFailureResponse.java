package org.request.retrofit.response;

import org.request.http.request.response.Response;

import java.util.List;
import java.util.Map;

public class XFailureResponse implements FailureResponse{


    private Response response;

    public XFailureResponse(Response response) {
        this.response = response;
    }

    @Override
    public String data() {
        return response.data();
    }

    @Override
    public String getRequestMethod() {
        return response.getRequestMethod();
    }

    @Override
    public int getCode() {
        return response.getCode();
    }

    @Override
    public int getContentLength() {
        return response.getContentLength();
    }

    @Override
    public String getContentType() {
        return response.getContentType();
    }

    @Override
    public String getHeader(String key) {
        return response.getHeader(key);
    }

    @Override
    public List<String> getHeaders(String key) {
        return response.getHeaders(key);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return response.getHeaders();
    }

    @Override
    public String toString() {
        return "XFailureResponse{" +
                "response=" + response +
                '}';
    }
}

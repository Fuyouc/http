package org.request.http.exception;

public class HttpRequestException extends RuntimeException{
    public HttpRequestException() {
    }

    public HttpRequestException(String message) {
        super(message);
    }
}

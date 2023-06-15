package org.request.retrofit.exception;

public class RetrofitException extends RuntimeException{
    public RetrofitException() {
    }

    public RetrofitException(String message) {
        super(message);
    }
}

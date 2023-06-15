package org.request.http.request.child;

import org.request.http.request.NetworkRequest;
import org.request.http.config.NetWorkConfiguration;
import org.request.http.request.Request;
import org.request.http.request.response.RequestResponse;
import org.request.http.request.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractNetworkRequest implements NetworkRequest {
    protected void autoConfiguration(HttpURLConnection connection,Request request){
        Iterator<Map.Entry<String, String>> iterator = request.getHeader().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            connection.setRequestProperty(entry.getKey(),entry.getValue());
        }

        NetWorkConfiguration configuration = request.getConfiguration();
        if (configuration != null){
            connection.setConnectTimeout(configuration.getConnectionTimeout());
            connection.setReadTimeout(connection.getReadTimeout());
            connection.setUseCaches(connection.getUseCaches());
        }
    }

    protected Response getResponse(HttpURLConnection connection, InputStream inputStream, String data) throws IOException {
        Response response = new RequestResponse(
                data,
                connection.getResponseCode(),
                connection.getRequestMethod(),
                connection.getContentLength(),
                connection.getContentType(),
                connection.getHeaderFields(),
                inputStream
        );
        return response;
    }

    protected Response getErrorResponse(String msg){
        return new RequestResponse(msg);
    }
}

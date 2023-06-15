package org.request.http.request.child;

import org.json.ValueUtils;
import org.request.http.request.Request;
import org.request.http.request.XRequest;
import org.request.http.request.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetRequest extends AbstractNetworkRequest {

    private String regex = "[\u4e00-\u9fa5]+";

    @Override
    public Response request(Request request) {
        try {
            HttpURLConnection connection = null;
            try {
                final String url;
                url = getURL(request);
                connection = (HttpURLConnection) new URL(url).openConnection();
                autoConfiguration(connection,request);
                connection.setRequestMethod(request.getMethod());
                InputStream inputStream = connection.getResponseCode() == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream();
                if (inputStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    return getResponse(connection, inputStream, sb.toString());
                }else {
                    return getResponse(connection, null, "未获取服务器响应数据");
                }
            }catch (Exception e){
                return getErrorResponse(e.getMessage());
            }finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getURL(Request request) throws UnsupportedEncodingException {
        ((XRequest)request).reconfigurationParam();
        StringBuilder sb = new StringBuilder(request.getURL());
        sb.append("?");
        Iterator<Map.Entry<String, Object>> iterator = request.getParams().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (List.class.isInstance(value)){
                List<Object> values = (List<Object>) value;
                for (Object old : values) {
                    sb.append(key + "=" + getValue(old) + "&");
                }
            }else {
                sb.append(key + "=" + getValue(value) + "&");
            }

        }
        if (request.getParams().size() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private Object getValue(Object value) throws UnsupportedEncodingException {
        if (String.class.isInstance(value)){
            String newValue = (String) value;
            newValue = newValue.matches(regex) ? URLEncoder.encode(newValue,"UTF-8") : newValue;
            return newValue;
        }
        return value;
    }
}

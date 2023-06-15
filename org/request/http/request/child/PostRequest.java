package org.request.http.request.child;

import com.alibaba.fastjson2.JSONObject;
import org.request.http.request.Request;
import org.request.http.request.XRequest;
import org.request.http.request.response.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PostRequest extends AbstractNetworkRequest{
    @Override
    public Response request(Request request) {
        try {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(request.getURL()).openConnection();
                autoConfiguration(connection,request);
                connection.setRequestMethod(request.getMethod());
                connection.setDoOutput(true);
                setBody(connection,request);
                connection.connect();
                InputStream inputStream = connection.getResponseCode() == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream();
                if (inputStream != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, len);
                    }
                    String data = byteArrayOutputStream.toString("UTF-8");
                    return getResponse(connection, inputStream, data,request.getURL());
                }else {
                    return getResponse(connection, null, "未获取服务器响应数据",request.getURL());
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

    private void setBody(HttpURLConnection connection,Request request) throws Exception {
        switch (request.getContentType()){
            case APPLICATION_JSON:
                bodyJson(connection,request);
                break;
            case APPLICATION_FROM_ENCODED:
                ((XRequest)request).reconfigurationParam();
                bodyFromEncode(connection,request);
                break;
            case MULTIPART_FROM_DATA:
                ((XRequest)request).reconfigurationParam();
                bodyFromData(connection,request);
                break;
            default:
                break;
        }
    }

    private void bodyJson(HttpURLConnection connection,Request request) throws Exception {
        byte[] data = JSONObject.toJSONString(request.getParams()).getBytes("UTF-8");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    private void bodyFromEncode(HttpURLConnection connection,Request request) throws Exception{
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = request.getParams().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (List.class.isInstance(value)){
                List<Object> values = (List<Object>) value;
                for (Object old : values) {
                    sb.append(key + "=" + old + "&");
                }
            }else {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        if (request.getParams().size() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        byte[] data = sb.toString().getBytes("UTF-8");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    private void bodyFromData(HttpURLConnection connection,Request request) throws Exception{
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------123456789");

        // 构造请求体
        OutputStream outputStream = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

        Iterator<Map.Entry<String, Object>> iterator = request.getParams().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List){
                List<Object> values = (List<Object>) value;
                for (Object old : values) {
                    bodyObject(key,old,writer,outputStream);
                }
            }else {
                bodyObject(key,value,writer,outputStream);
            }
        }

        // 请求体结束符
        writer.append("-----------------------------123456789--\r\n");
        writer.flush();
    }

    private void bodyObject(String key,Object value,PrintWriter writer,OutputStream outputStream) throws Exception {
        if (value instanceof File){
            // 添加文件字段
            File file = (File) value;
            writer.append("-----------------------------123456789\r\n");
            writer.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\"" + file.getName() + "\"\r\n");
            writer.append("Content-Type: application/octet-stream\r\n\r\n");
            writer.flush();

            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append("\r\n");
        }else {
            bodyString(key,value,writer);
        }
    }

    private void bodyString(String key,Object value,PrintWriter writer){
        // 添加普通表单字段
        writer.append("-----------------------------123456789\r\n");
        writer.append("Content-Disposition: form-data; name=\""+key+"\"\r\n\r\n");
        writer.append(""+value+"\r\n");
    }

}

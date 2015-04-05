package com.yastart.papaya.Model;

import com.loopj.android.http.*;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Server {
    private static final String BASE_URL = "https://papaya-dtd.appspot.com/_ah/api/papaya/v1/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        // params is a JSONObject
        StringEntity se = null;
        try {
            se = new StringEntity(params.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        client.post(null, getAbsoluteUrl(url), se, "application/json", responseHandler);
    }

    public static void post(String url, JSONObject params, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(params.toString());
//        entity.set
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

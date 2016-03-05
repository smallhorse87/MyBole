package com.zhitian.mybole.api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.Locale;

public class ApiHttpClient {
	public final static String HOST = "m.bodimall.com";
	private static String API_URL;
	public static String DEFAULT_API_URL;
	public static final String DELETE = "DELETE";
	public static String DEV_API_URL = "http://m.bodimall.com/api/%s";
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static AsyncHttpClient client;

	static {
		API_URL = DEV_API_URL;
	}

	public ApiHttpClient() {
	}

	public static AsyncHttpClient getHttpClient() {
		return client;
	}

	public static void cancelAll(Context context) {
		client.cancelRequests(context, true);
	}

	public static void delete(String partUrl, AsyncHttpResponseHandler handler) {
		client.delete(getAbsoluteApiUrl(partUrl), handler);
		log(new StringBuilder("DELETE ").append(partUrl).toString());
	}

	public static void get(String partUrl, AsyncHttpResponseHandler handler) {
		client.get(getAbsoluteApiUrl(partUrl), handler);
		log(new StringBuilder("GET ").append(partUrl).toString());
	}

	public static void get(String partUrl, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.get(getAbsoluteApiUrl(partUrl), params, handler);
		log(new StringBuilder("GET ").append(partUrl).append("&")
				.append(params).toString());
	}

	public static String getAbsoluteApiUrl(String partUrl) {
		String url = String.format(API_URL, partUrl);
		Log.d("BASE_CLIENT", "request:" + url);
		return url;
	}

	public static String getApiUrl() {
		return API_URL;
	}

	public static void log(String log) {
		//TLog.log("BaseApi", log); stony debug
	}

	public static void post(String partUrl, AsyncHttpResponseHandler handler) {
		client.post(getAbsoluteApiUrl(partUrl), handler);
		log(new StringBuilder("POST ").append(partUrl).toString());
	}

	public static void post(String partUrl, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.post(getAbsoluteApiUrl(partUrl), params, handler);
		log(new StringBuilder("POST ").append(partUrl).append("&")
				.append(params).toString());
	}

	public static void post(String partUrl, RequestParams params,
							JsonHttpResponseHandler jsonhandler) {
		client.post(getAbsoluteApiUrl(partUrl), params, jsonhandler);
		log(new StringBuilder("POST ").append(partUrl).append("&")
				.append(params).toString());
	}

	//newJsonHttpResponseHandler

	public static void put(String partUrl, AsyncHttpResponseHandler handler) {
		client.put(getAbsoluteApiUrl(partUrl), handler);
		log(new StringBuilder("PUT ").append(partUrl).toString());
	}

	public static void put(String partUrl, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.put(getAbsoluteApiUrl(partUrl), params, handler);
		log(new StringBuilder("PUT ").append(partUrl).append("&")
				.append(params).toString());
	}


	public static void setApiUrl(String apiUrl) {
		API_URL = apiUrl;
	}

	public static void setHttpClient(AsyncHttpClient c) {
		client = c;
		client.addHeader("Accept-Language", Locale.getDefault().toString());
		client.addHeader("Host", HOST);
		client.addHeader("Connection", "Keep-Alive");
	}

}

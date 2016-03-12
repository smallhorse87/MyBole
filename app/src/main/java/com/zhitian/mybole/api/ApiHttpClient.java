package com.zhitian.mybole.api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.utils.TDevice;
import com.zhitian.mybole.utils.TLog;


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

	public static void put(String partUrl, AsyncHttpResponseHandler handler) {

		client.put(getAbsoluteApiUrl(partUrl), attachParams(null), handler);
		log(new StringBuilder("PUT ").append(partUrl).toString());
	}

	public static void delete(String partUrl, AsyncHttpResponseHandler handler) {
		client.delete(getAbsoluteApiUrl(partUrl), attachParams(null), handler);
		log(new StringBuilder("DELETE ").append(partUrl).toString());
	}

	public static void getWithFullUrl(String fullUrl, RequestParams params,
						   AsyncHttpResponseHandler handler) {
		client.get(fullUrl, attachParams(params), handler);
		log(new StringBuilder("GET ").append(fullUrl).append("&")
				.append(params).toString());
	}

	public static void get(String partUrl, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.get(getAbsoluteApiUrl(partUrl), attachParams(params), handler);
		log(new StringBuilder("GET ").append(partUrl).append("&")
				.append(params).toString());
	}

	public static void post(String partUrl, RequestParams params,
							AsyncHttpResponseHandler handler) {
		client.post(getAbsoluteApiUrl(partUrl), attachParams(params), handler);
		log(new StringBuilder("POST ").append(partUrl).append("&")
				.append(params).toString());
	}

	public static void post(String partUrl, RequestParams params,
							JsonHttpResponseHandler jsonhandler) {
		client.post(getAbsoluteApiUrl(partUrl), attachParams(params), jsonhandler);
		log(new StringBuilder("POST ").append(partUrl).append("&")
				.append(params).toString());
	}

	public static void cancelAll(Context context) {
		client.cancelRequests(context, true);
	}

	public static String getAbsoluteApiUrl(String partUrl) {
		String url = String.format(API_URL, partUrl);
		Log.d("BASE_CLIENT", "request:" + url);
		return url;
	}

	public static void log(String log) {
		TLog.log("BaseApi", log);
	}

	public static void setHttpClient(AsyncHttpClient c) {
		client = c;
		client.addHeader("Accept-Language", Locale.getDefault().toString());
		client.addHeader("Host", HOST);
		client.addHeader("Connection", "Keep-Alive");
	}

	public static RequestParams attachParams(RequestParams params)
	{
		if(params == null){
			params = new RequestParams();
		}

		params.put("uuid", TDevice.getUdid());

		if (AppContext.getUid() != null)
			params.put("uid",  AppContext.getUid());

		if (AppContext.getGsid() != null)
			params.put("gsid", AppContext.getGsid());

//		params.put("c", "android");
//		params.put("os", "android");
//		params.put("ua", "android");
//		params.put("cv", "android");
//		params.put("sv", "android");
//		params.put("cfg", "android");

		return params;

	}

}

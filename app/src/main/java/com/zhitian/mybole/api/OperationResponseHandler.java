package com.zhitian.mybole.api;


import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.google.gson.Gson;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.entity.ConfigInfo;
import com.zhitian.mybole.entity.MerchantInfo;

import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public abstract class OperationResponseHandler extends JsonHttpResponseHandler {


	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

		Gson gson = new Gson();
		ApiResult result = gson.fromJson(response.toString(), ApiResult.class);

		if (result.getRet() == 0) {
			try {
				JSONObject retData = response.getJSONObject("data");
				onJsonSuccess(retData);
			} catch (Exception e) {
				onJsonFailure(-1, "unexcepted error");
			}
		} else {
			onJsonFailure(result.getRet(), result.getMsg());
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
		onJsonFailure(statusCode, responseString);
	}

	public abstract void onJsonSuccess(JSONObject retData);
	public abstract void onJsonFailure(int statusCode, String errMsg);

	public MerchantInfo retLoginSucc(JSONObject retData)
	{
		try {
			JSONObject merchantInfoJson = retData.getJSONObject("merchantInfo");
			String gsid = retData.getString("gsid");

			Gson gson = new Gson();
			MerchantInfo info = gson.fromJson(merchantInfoJson.toString(), MerchantInfo.class);

			info.setGsid(gsid);

			return info;

		}	catch (Exception e) {
			return null;
		}

	}

	public ConfigInfo retSystemConfig(JSONObject retData)
	{
		try {
			Gson gson = new Gson();
			ConfigInfo info = gson.fromJson(retData.toString(), ConfigInfo.class);

			AppContext.cfgInfo = info;

			//download region xml, if needed

			return info;

		}	catch (Exception e) {

			return null;
		}

	}
}

package com.zhitian.mybole.api;


import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.google.gson.Gson;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.activity.statistics.TotalStatActivity;
import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.ConfigInfo;
import com.zhitian.mybole.entity.DetailedStatInfo;
import com.zhitian.mybole.entity.MerchantInfo;
import com.zhitian.mybole.entity.PageInfo;
import com.zhitian.mybole.entity.PlanInfo;
import com.zhitian.mybole.entity.TotalStatInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public abstract class OperationResponseHandler extends JsonHttpResponseHandler {


	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

		Gson gson = new Gson();
		ApiResult result = gson.fromJson(response.toString(), ApiResult.class);

		if (result.getRet() == 0) {
			try {
				Object retData = response.get("data");

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

	public abstract void onJsonSuccess(Object retData);
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

			return info;

		}	catch (Exception e) {

			return null;
		}

	}

	public PageInfo retPageInfo(JSONObject retData)
	{
		try {
			Gson gson = new Gson();
			PageInfo info = gson.fromJson(retData.toString(), PageInfo.class);

			return info;

		}	catch (Exception e) {

			return null;
		}

	}

	public List<PlanInfo> retPlanInfoList(JSONArray retArr)
	{
		try {

			List<PlanInfo> infos = new ArrayList<PlanInfo>();

			for (int idx = 0; idx < retArr.length(); idx ++){
				JSONObject item = (JSONObject)retArr.get(idx);

				Gson gson = new Gson();
				infos.add((PlanInfo) gson.fromJson(item.toString(), PlanInfo.class));
			}

			return infos;

		}	catch (Exception e) {

			return null;
		}

	}

	public List<ActivityInfo> retActivityList(JSONObject retData)
	{
		try {

			JSONArray retArr = retData.getJSONArray("retArr");

			List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();//getListFromJSON(retArr.toString(), ActivityInfo.class);

			for (int idx = 0; idx < retArr.length(); idx ++){
				JSONObject item = (JSONObject)retArr.get(idx);

				Gson gson = new Gson();
				activityInfos.add((ActivityInfo) gson.fromJson(item.toString(), ActivityInfo.class));
			}

			return activityInfos;

		}	catch (Exception e) {

			return null;
		}

	}

	public TotalStatInfo retStatsList(JSONObject retData)
	{
		try {

			Gson gson = new Gson();

			return gson.fromJson(retData.toString(), TotalStatInfo.class);

		}	catch (Exception e) {

			return null;
		}

	}

	public DetailedStatInfo retDetailedStat(JSONObject retData)
	{
		try {

			Gson gson = new Gson();

			return gson.fromJson(retData.toString(), DetailedStatInfo.class);

		}	catch (Exception e) {

			return null;
		}

	}

	public <T> List<T> getListFromJSON(String str, Class<T> type) {
		Type listType = new TypeToken<ArrayList<T>>() {
		}.getType();
		List<T> list = new Gson().fromJson(str, listType);
		return list;
	}
}
package org.kona.andorid;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public abstract class KonaRequest {

	private static final String URL_EMPTY = "URL Malformed";
	private static final String SUCCESS = "success";
	protected static final String NULL_RESPONSE = "null response form the server";
	private static final String LOG_TAG = "KonaRequest";
	private static final String X_ACCESS_TOKEN = "X-AUTH-TOKEN";

	public static boolean getallJson = false;

	protected String url;
	protected HTTPMethod method;
	protected String data;
	protected String accessToken = null;

	protected Map<String, String> headers = new HashMap<String, String>();

	public abstract void onSuccess(String jsonObject);

	public abstract void onFailure(KonaResponse res);

	public void make() {
		if (url == null) {
			sendFailure(URL_EMPTY);
		}
		if (method == null) {
			method = HTTPMethod.GET;
		}
		asyncRequest();
	}

	private void sendFailure(String msg) {
		KonaResponse res = new KonaResponse();
		res.setMsg(msg);
		onFailure(res);
	}

	public void asyncRequest() {
		new AsyncTask<Void, KonaResponse, KonaResponse>() {

			protected KonaResponse doInBackground(Void... params) {
				try {
					return sendRequest();
				} catch (Exception e) {
					e.printStackTrace();
				}

				KonaResponse res = new KonaResponse();
				res.setSuccess(false);
				res.setMsg(NULL_RESPONSE);
				return res;
			}

			protected void onPostExecute(KonaResponse result) {
				if (result != null && result.isSuccess()) {
					onSuccess(result.getData());
				} else {
					onFailure(result);
				}
			};

		}.execute();
	}

	protected KonaResponse sendRequest() throws Exception {

		if (accessToken != null && headers != null) {
			headers.put(X_ACCESS_TOKEN, accessToken);

		}

		safeLog("Sending HTTP " + method + " to " + url);
		if (method.equals(HTTPMethod.GET)) {
			return sendGetRequest();
		} else if (method.equals(HTTPMethod.POST)) {
			return sendPostRequest();
		} else if (method.equals(HTTPMethod.PUT)) {
			return sendPutRequest();
		} else {
			return sendDeleteRequest();
		}
	}

	private void safeLog(String string) {

		try {
			Log.v(LOG_TAG, string);
		} catch (Exception e) {
		}

	}

	private KonaResponse sendGetRequest() {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HTTPUtil.addRequestHedaers(httpGet, headers);

		try {
			HttpResponse execute = client.execute(httpGet);
			return parseHTTPResponse(execute);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private KonaResponse sendPostRequest() throws Exception {

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(parseDataToJson(data)));
		HTTPUtil.addRequestHedaers(httpPost, headers);

		HttpResponse res = new DefaultHttpClient().execute(httpPost);
		try {
			return parseHTTPResponse(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private KonaResponse sendDeleteRequest() throws Exception {

		HttpDelete httpdelete = new HttpDelete(url);
		HTTPUtil.addRequestHedaers(httpdelete, headers);

		HttpResponse res = new DefaultHttpClient().execute(httpdelete);
		try {
			return parseHTTPResponse(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private KonaResponse sendPutRequest() throws Exception {

		HttpPut httpPut = new HttpPut(url);
		httpPut.setEntity(new StringEntity(parseDataToJson(data)));
		HTTPUtil.addRequestHedaers(httpPut, headers);

		HttpResponse res = new DefaultHttpClient().execute(httpPut);

		try {
			return parseHTTPResponse(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String parseDataToJson(String data) {

		safeLog("Entity " + data);
		if (data instanceof String)
			return (String) data;
		return data.toString();

	}

	protected KonaResponse parseHTTPResponse(HttpResponse execute)
			throws Exception {

		String response = HTTPUtil.getStringFromHttpResponse(execute);

		safeLog("Response " + response);

		KonaResponse res = new KonaResponse();
		res.setCode(execute.getStatusLine().getStatusCode());

		if (getallJson) {

			if (res.getCode() == 200) {
				res.setSuccess(true);
			} else {
				res.setSuccess(false);
			}
			res.setData(response);
			return res;
		}

		JSONObject jsonObject = new JSONObject(response);

		if (res.getCode() == 200 && jsonObject.getBoolean(SUCCESS)) {
			res.setSuccess(true);

			res.setData(jsonObject.getString("data"));

		} else {
			Log.e(LOG_TAG, "Error: " + res.getMsg());

			res.setSuccess(false);
			if (!jsonObject.isNull("msg")) {
				res.setMsg(jsonObject.getString("msg"));
			}

		}

		return res;
	}

}

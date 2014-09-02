/**
 * 
 */
package org.kona.andorid.buckets;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kona.andorid.HTTPUtil;
import org.kona.andorid.KonaResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author santiago
 * 
 */
public class KonaBucket {

	private static KonaBucket _instance = new KonaBucket();

	public static KonaBucket getInstance() {
		return _instance;
	}

	public interface KonaCallBack {

		void receive(String url);
	}

	public void uploadImage(String url, byte[] bytes, KonaCallBack callback)
			throws ClientProtocolException, IOException {

		asyncRequest(url, bytes, callback);
	}

	public void asyncRequest(final String url, final byte[] bytes,
			final KonaCallBack callback) {
		new AsyncTask<Void, String, String>() {

			protected String doInBackground(Void... params) {
				try {
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams
							.setConnectionTimeout(httpParams, 60000); // 1 min
					HttpConnectionParams.setSoTimeout(httpParams, 60000); // 1
																			// min

					HttpClient client = new DefaultHttpClient(httpParams);
					HttpPost post = new HttpPost(url);

					List<NameValuePair> pairs = new ArrayList<NameValuePair>();

					ContentBody mimePart = new ByteArrayBody(bytes, "filename");

					MultipartEntity reqEntity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);
					reqEntity.addPart("filename", mimePart);

					// post.setEntity(new UrlEncodedFormEntity(pairs,
					// HTTP.UTF_8)); // as
					// UTF-8
					post.setEntity(reqEntity);
					HttpResponse response = client.execute(post);

					String res = HTTPUtil.getStringFromHttpResponse(response);

					return res;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return url;
			}

			protected void onPostExecute(String result) {

				try {

					JSONObject json = new JSONObject(result);
					String arr = json.getJSONArray("data").getJSONObject(0)
							.getString("url");
					callback.receive(arr);
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				callback.receive(null);
			};

		}.execute();
	}

	public void loadImage(String url, ImageView img) {
		new DownloadImageTask(img).execute(url);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			Bitmap resized2 = Bitmap.createScaledBitmap(result, 70, 70, true);

			bmImage.setImageBitmap(resized2);
		}
	}
}

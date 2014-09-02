package org.kona.andorid.test;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;
import org.kona.andorid.HTTPMethod;
import org.kona.andorid.KonaRequest;
import org.kona.andorid.KonaResponse;

import android.widget.Toast;

public class KonaRequestTest extends TestCase {

	@Test
	public void testGET() throws Exception {

		KonaRequest request = new KonaRequest() {
			{
				this.url = "http://app.konacloud.io/api/taio/hello/r_api";
				this.method = HTTPMethod.GET;
				this.accessToken = "5b7fb5bd-addc-4d72-8fb3-3e2b90fcbf69";
			}

			@Override
			public void onSuccess(String jsonObject) {
				assertNotNull(jsonObject);
			}

			@Override
			public void onFailure(KonaResponse res) {
				assertEquals(false, true);
				
			}
		};
		request.make();

		Thread.sleep(5 * 1000);
	}

	@Test
	public void testPost() throws Exception {

		final JSONObject json = new JSONObject();
		json.put("name", "kona");

		KonaRequest request = new KonaRequest() {
			{
				this.url = "http://app.konacloud.io/api/taio/hello/r_api";
				this.method = HTTPMethod.POST;
				this.data = json.toString();
				this.accessToken = "5b7fb5bd-addc-4d72-8fb3-3e2b90fcbf69";
			}

			@Override
			public void onSuccess(String jsonObject) {
				assertNotNull(jsonObject);
			}

			@Override
			public void onFailure(KonaResponse res) {
				assertEquals(false, true);

			}
		};
		request.make();

		Thread.sleep(5 * 1000);
	}

	@Test
	public void testPut() throws Exception {

		final JSONObject json = new JSONObject();
		json.put("name", "kona");

		KonaRequest request = new KonaRequest() {
			{
				this.url = "http://app.konacloud.io/api/taio/hello/r_api";
				this.method = HTTPMethod.PUT;
				this.data = json.toString();
				this.accessToken = "5b7fb5bd-addc-4d72-8fb3-3e2b90fcbf69";
			}

			@Override
			public void onSuccess(String jsonObject) {
				assertNotNull(jsonObject);
			}

			@Override
			public void onFailure(KonaResponse res) {
				assertEquals(false, true);

			}
		};
		request.make();

		Thread.sleep(5 * 1000);

	}

	@Test
	public void testDelete() throws Exception {

		KonaRequest request = new KonaRequest() {
			{
				this.url = "http://app.konacloud.io/api/taio/hello/r_api";
				this.method = HTTPMethod.DELETE;
				this.accessToken = "5b7fb5bd-addc-4d72-8fb3-3e2b90fcbf69";
			}

			@Override
			public void onSuccess(String jsonObject) {
				assertNotNull(jsonObject);
			}

			@Override
			public void onFailure(KonaResponse res) {
				assertEquals(false, true);

			}
		};
		request.make();

		Thread.sleep(5 * 1000);
	}
}

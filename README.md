#Android SDK

## Download

https://github.com/konacloud/sdk/releases/latest

## Install

Just add the project as a library

## Dependencies

No dependencies for restClient

If you use buckets add this apache http mime
http://repo1.maven.org/maven2/org/apache/httpcomponents/httpmime/4.2.1/httpmime-4.2.1.jar

for converting Pojos into Strings we recomend to use Gson https://code.google.com/p/google-gson/

## Configuration

AndroidManigest.xml

```
<uses-permission android:name="android.permission.INTERNET"></uses-permission>
```

## Uses

### HTTP GET

```
	KonaRequest request = new KonaRequest() {
	{
		this.url = "http://app.konacloud.io/...";
		this.method = HTTPMethod.GET;
		this.accessToken = "5b7...";
	}
	
	@Override
	public void onSuccess(String jsonObject) {
		//do something on success
	}
	
	@Override
	public void onFailure(KonaResponse res) {
		//do something on failure
		Toast.makeText(getActivity(), res.getMsg(), Toast.LENGTH_LONG).show();
	}
	};
	request.make();
```

### HTTP POST

```
	final JSONObject json = new JSONObject();
	json.put("name", "kona");
	
	KonaRequest request = new KonaRequest() {
	{
		this.url = "http://app.konacloud.io/..";
		this.method = HTTPMethod.POST;
		this.data = json.toString();
		this.accessToken = "5b7fb5bd..";
	}
	
	@Override
	public void onSuccess(String jsonObject) {
		
	}
	
	@Override
	public void onFailure(KonaResponse res) {
	
	}
	};
	request.make();
```

### HTTP PUT

```
	final JSONObject json = new JSONObject();
	json.put("name", "kona");
	
	KonaRequest request = new KonaRequest() {
	{
		this.url = "http://app.konacloud.io/..";
		this.method = HTTPMethod.PUT;
		this.data = json.toString();
		this.accessToken = "5b7fb...";
	}
	
	@Override
	public void onSuccess(String jsonObject) {
		
	}
	
	@Override
	public void onFailure(KonaResponse res) {
	
	}
	};
	request.make();
```

#### HTTP Delete

```
	KonaRequest request = new KonaRequest() {
	{
		this.url = "http://app.konacloud.io/..";
		this.method = HTTPMethod.DELETE;
		this.accessToken = "5b7fb5bd..";
	}
	
	@Override
	public void onSuccess(String jsonObject) {
		
	}
	
	@Override
	public void onFailure(KonaResponse res) {
	
	}
	};
	request.make();
```



### GSON Integration

Download https://code.google.com/p/google-gson/downloads/list

#### Instanciate the Gson object
```
Gson gson = new Gson();
```

#### ToString();

```
MyPojo obj = new MyPojo();
String json = gson.toJson(obj);
```

#### POST Example

```
	Gson gson = new Gson();
	
	MyPojo obj = new MyPojo();
	obj.setName("Kona");
	
	final String json = gson.toJson(obj);
	
	KonaRequest request = new KonaRequest() {
	{
		this.url = "http://app.konacloud.io/..";
		this.method = HTTPMethod.POST;
		this.data = json;
		this.accessToken = "5b7fb5bd..";
	}
	
	@Override
	public void onSuccess(String jsonObject) {
		
	}
	
	@Override
	public void onFailure(KonaResponse res) {
	
	}
	};
	request.make();
```

### Buckets

#### Post a FILE

```
	KonaCallBack callback = new KonaCallBack() {

					@Override
					public void receive(String url) {
						Log.v(this.getClass().toString(), "url: " + url);
					}
				};
				KonaBucket
						.getInstance()
						.uploadImage(
								"http://bucket.konacloud.io/external/api/bucket/taio/hello/b1",
								byte_arr, callback);
```								
#### Example

Take a photo with the camera and send to a KONA backet storage.

```

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			// mImageView.setImageBitmap(imageBitmap);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
			byte[] byte_arr = stream.toByteArray();

			try {

				KonaCallBack callback = new KonaCallBack() {

					@Override
					public void receive(String url) {
						Log.v(this.getClass().toString(), "url: " + url);
					}
				};
				KonaBucket
						.getInstance()
						.uploadImage(
								"http://bucket.konacloud.io/external/api/bucket/taio/hello/b1",
								byte_arr, callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
```

#### Load a File in ImageView

```
	KonaBucket.getInstance().loadImage("http://host",imageView);
```

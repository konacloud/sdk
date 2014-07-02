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

# JS (Jquery)

We recommend to use jquery.

```
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
```

Below are examples of post and gets

## POST

For this model example

![ScreenShot](http://i.imgur.com/SRcAHrul.png)


```
var obj = {
  name: "myName",
  email: "myEmail@company.com"
}

$.ajax
    ({
        type: "POST",
        url: 'http://app.konacloud.io/user/app/modelId',
        dataType: 'json',
        data: JSON.stringify(obj),
        success: function (data) {
          console.log(data);
        }
})
```

## GET

```
$.getJSON( "http://app.konacloud.io/user/app/modelId", function( data ) {
    if (!data.success){
      console.log("some error happend " + data.msg);
    }else{
        console.log(data.data);
    }
});
```

### Simple working example

```
<html>
<head>
<title>KONA jQuery Hello World</title>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
</head>
<body>
<script type="text/javascript">

var URL = "http://app.konacloud.io/api/taio/samples/mr_User"
var obj = {
  name: "myName",
  email: "myEmail@company.com"
}
$.ajax
    ({
        type: "POST",
        url: URL,
        dataType: 'json',
        data: JSON.stringify(obj),
        success: function (data) {
          console.log(data);
        }
})
$.getJSON( URL, function( data ) {
    if (!data.success){
      console.log("some error happend " + data.msg);
    }else{
        console.log(data.data);
    }
});

</script>
This is Hello World by KONA Cloud Jquery Example - Just press F5 to insert and get
View the console for results
</body>
</html>

```

## X-AUTH-TOKEN

```
$.ajax
    ({
        type: "POST",
         beforeSend: function (request)
         {
                request.setRequestHeader("X-AUTH-TOKEN", "e0899540-f8f4-40e7-a1f8-d18f4bf521e4");
         }
        url: URL,
        dataType: 'json',
        data: JSON.stringify(obj),
        success: function (data) {
          console.log(data);
        }
})
```

## File Upload to ha KONA Bucket Storage

We recommend to use this Jquery plugin

https://github.com/blueimp/jQuery-File-Upload

### Simple usage

```
<input id="fileupload" type="file" name="files[]" multiple>
```
```
$('#fileupload').fileupload({
        url: "http://bucket.konacloud.io/external/api/bucket/taio/samples/b1",
        dataType: 'json',
        done: function (e, data) {
            console.log(data.result.data[0].url);
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
```

### Complete example

```
<!DOCTYPE HTML>

<html lang="en">
<head>
<meta charset="utf-8">
<title>jQuery File Upload Demo - Basic version</title>
<meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support and progress bar for jQuery. Supports cross-domain, chunked and resumable file uploads. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads.">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/jquery.fileupload.css">
</head>
<body>

<div class="container">
    <h1>KONA jQuery File Upload</h1>

    <span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>Select files...</span>
        <input id="fileupload" type="file" name="files[]" multiple>
    </span>
    <br>
    <br>
    <div id="progress" class="progress">
        <div class="progress-bar progress-bar-success"></div>
    </div>
    <div id="files" class="files"></div>
    <br>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<script src="js/vendor/jquery.ui.widget.js"></script>

<script src="js/jquery.iframe-transport.js"></script>

<script src="js/jquery.fileupload.js"></script>

<script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script>
/*jslint unparam: true */
/*global window, $ */
$(function () {
    'use strict';
    // Change this to the location of your server-side upload handler:

    $('#fileupload').fileupload({
        url: "http://bucket.konacloud.io/external/api/bucket/taio/samples/b1",
        dataType: 'json',
        done: function (e, data) {
            console.log("hola");
            console.log(data.result.data[0].url);
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
});
</script>
</body>
</html>

```


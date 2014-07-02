#Android SDK

## Download

https://github.com/konacloud/sdk/releases/latest

## Install

Just add the project as a library

## Dependencies

No dependencies

We recomend to use Gson https://code.google.com/p/google-gson/ for converting Pojos into Strings

## Configuration

None

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

## GSON Integration

Download https://code.google.com/p/google-gson/downloads/list

### Instancite the Gson object
```
Gson gson = new Gson();
```

### ToString();

```
MyPojo obj = new MyPojo();
String json = gson.toJson(obj);
```

### POST Example

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

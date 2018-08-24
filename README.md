# NetWork
retrofit2 + okhttp3 网络请求封装库
## 配置
### 在根构建中添加它
    allprojects {
		  repositories {
		  	...
		  	maven { url 'https://jitpack.io' }
		 }
	}
  
### 添加依赖关系
    dependencies {
	        implementation 'com.github.tangguna:NetWork:1.0.1'
	 }

### 初始化
#### 在Android studio项目Application中配置
     @Override
     public void onCreate() {
        super.onCreate();
        RequestClient.init(getApplicationContext(), BASE_PATH,null);
    }
三个参数分别是：<br>
getApplicationContext()：上下文；<br>
BASE_PATH：基地址（例如：http://192.168.1.1:8081)；<br>
null:此参数为一个Map<String,String>对象，用于配置网络请求header头，如果有则配置，没有则置为空。<br>

### 基本使用
#### GET请求
        RequestClient.builder()
                .url(url)
                .params("account","13000000000")
                .params("password","111111")
                .success(new SuccessListener() {
                    @Override
                    public void success(String s) {
                        //数据解析提取
                        Login login = GsonUtils.fromJson(s,Login.class);
                        Log.e("get输出",s.toString());
                    }
                })
                .failure(new FailureListener() {
                    @Override
                    public void failure() {
                        Log.e("get输出","失败");
                    }
                })
                .error(new ErrorListener() {
                    @Override
                    public void error(int i, String s) {
                        Log.e("get输出",i +","+s.toString());
                    }
                }).build().get();
		
		
#### POST请求
      RequestClient.builder()
                .url(url)
                .params("account","13000000000")
                .params("password","111111")
                .success(new SuccessListener() {
                    @Override
                    public void success(String s) {
                        //数据解析提取
                        Login login = GsonUtils.fromJson(s,Login.class);
                        Log.e("post输出",s.toString());
                    }
                })
                .failure(new FailureListener() {
                    @Override
                    public void failure() {
                        Log.e("post输出","失败");
                    }
                })
                .error(new ErrorListener() {
                    @Override
                    public void error(int i, String s) {
                        Log.e("post输出",i +","+s.toString());
                    }
                }).build().post();
		
		
		
#### POST JSON请求
     RequestClient.builder()
                .url(url)
                .raw(json)
                .success(new SuccessListener() {
                    @Override
                    public void success(String s) {
                        //数据解析提取
                        Login login = GsonUtils.fromJson(s,Login.class);
                        Log.e("post输出",s.toString());
                    }
                })
                .failure(new FailureListener() {
                    @Override
                    public void failure() {
                        Log.e("post输出","失败");
                    }
                })
                .error(new ErrorListener() {
                    @Override
                    public void error(int i, String s) {
                        Log.e("post输出",i +","+s.toString());
                    }
                }).build().post();

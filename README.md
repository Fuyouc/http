# 1、介绍

**Java网络请求库**

# 2、使用

### Http

Http是由`JAVA URL`封装的网络请求库，内部封装了原生`URL`的操作和响应对象，让开发人员更加简便的使用Http网络请求的操作

**使用方式：**

```java
//构建http对象
Http http = new Http
        .Builder()
        /**
         * 设置网络拦截器
         * 发送请求前拦截该请求，获取请求对象
         * 接收响应数据时，获取响应体
         */
        .setInterceptor(new HttpInterceptro())
        .build();

//构建请求对象
Request request = new XRequest.Builder()
        .setURL("") //设置访问URL
        /**
         * 添加请求参数
         * 如果一个key对应多个value，底层会自动转换成一个List来保存
         */
        .addParam("key","value")
        .addHeader("key","value") //添加请求头
        .get()  //使用get请求方式
        .post() //使用post请求方式
        .setContentType(MediaType.APPLICATION_JSON) //如果使用POST请求，指定POST请求的方式
        .setConfiguration() //设置网络请求配置
        .setReadTimeout()  //设置读取超时时间
        .setConnectionTimeout() //设置连接超时时间
        .setUseCaches()  //是否开启缓存
        .build();

//获取发送网络请求对象
Call call = http.newCall(request);

//发送同步请求
Response response = call.execute();

//发送异步请求
call.enqueue(new HttpRequestCallBack() {
    @Override
    public void onFailure(FailureResponse failureResponse) {
        //失败回调
    }

    @Override
    public void onResponse(Response response) {
        //成功回调
    }
});
```

### Retrofit

`Retrofit`是基于`Http`的基础之上进行的封装，简化了手动创建`Request`的操作，利用`接口+注解`即可自动创建Request对象，极大简化了代码的耦合度。

**Retrofit提供了以下注解：**

| 注解         | 介绍                                                         |
| ------------ | ------------------------------------------------------------ |
| **@GET**     | 在接口方法上使用，表示该方法将以`GET`的请求方式发送网络请求  |
| **@POST**    | 在接口方法上使用，表示该方法将以`POST`的请求方式发送网络请求 |
| **@Param**   | 在方法参数上使用，指定参数在发送网络请求时的key名称（默认参数名称） |
| **@Header**  | 在接口方法上获取与`@Headers`配合使用，表示添加请求头         |
| **@Headers** | 在接口方法或者与参数上使用，如果是在方法上，则需要与`@Header`配合使用，如果是在参数上，则参数类型必须是`Map<String,String>`类型 |
| **@URL**     | 允许在接口上、接口方法上、方法参数上使用。在接口上，则表示指定该接口的对应的服务器ip，如果是在方法上，则表示该方法会想指定的`URL`发送请求，如果是在参数上，与方法上相同，但是优先级要高于方法（**注意：如果是在方法和参数上，那么URL就应该是完整的URL**） |

**使用：**

一：构建`Retrofit`

```java
/**
 * 构建Retrofit对象
 */
Retrofit retrofit = new Retrofit.Builder()
        .setService()     //设置服务器ip
        .setInterceptor() //设置拦截器
        .setReadTimeout() //设置读取超时时间
        .setConnectionTimeout() //设置连接超时时间
        .setUseCaches()  //是否使用缓存
        .build();
```

二：`创建服务接口，指定服务URI`

```java
/**
 * 创建服务接口
 */
public interface Service {

    /**
     * 表示该接口会以GET的请求方式访问 服务器ip:端口/user/getInfo
     * 参数会自动拼接到GET请求参数中。使用@Param可以指定参数key（默认使用参数名）
     * 接口返回值必须使用 Call<响应类型>，在Call的泛型中，可以指定任意的响应类型，Retrofit会将响应结果自动封装成该对象
     * 如果不需要返回值，则使用Call<Void>即可
     */
    @GET("/user/getInfo")
    Call<Map<String,Object>> getUserInfo(Map<String,Object> map, @Param("key名称") String key);


    /**
     * 表示该接口会以POST的请求方式访问 服务器ip:端口/user/getInfo
     * 请求类型为Application/json，并且携带cookie与token的请求头
     */
    @Post(value = "/user/login",contentType = MediaType.APPLICATION_JSON)
    @Header(key = "cookie",value = "cookie值")
    @Headers(header = {@Header(key = "token",value = "token值")})
    Call<Void> login(@Param("username") String name,@Param("password") String pwd);
}
```

三：创建服务接口对象，通过服务接口发送网络请求

```java
/**
 * 创建服务接口对象
 */
Service service = retrofit.getService(Service.class);

//发送网络请求
service.login("admin","password").enqueue(new Callback<Void>() {
    @Override
    public void onFailure(FailureResponse response) {

    }

    @Override
    public void onResponse(Response<Void> response) {

    }
});
```
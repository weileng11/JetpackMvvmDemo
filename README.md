[![Platform][1]][2] [![GitHub license][3]][4]  [![GitHub license][5]][6] 

[1]:https://img.shields.io/badge/platform-Android-blue.svg  
[2]:https://github.com/hegaojian/JetpackMvvm
[3]:https://img.shields.io/github/release/hegaojian/JetpackMvvm.svg
[4]:https://github.com/hegaojian/JetpackMvvm/releases/latest
[5]:https://img.shields.io/badge/license-Apache%202-blue.svg
[6]:https://github.com/hegaojian/JetpackMvvm/blob/master/LICENSE

# JetPackMvvm
- **基于MVVM模式集成谷歌官方推荐的JetPack组件库：LiveData、ViewModel、Lifecycle、Navigation组件**
- **使用kotlin语言，添加大量拓展函数，简化代码**
- **加入Retorfit网络请求,协程，帮你简化各种操作，让你快速请求网络**  

## 演示Demo
 已用该库重构了我之前的玩安卓项目，利用Navigation组件以单Activity+Fragment架构编写，优化了很多代码，对比之前的mvp项目，开发效率与舒适度要提高了不少，想看之前MVP的项目可以去 [https://github.com/hegaojian/WanAndroid](https://github.com/hegaojian/WanAndroid) 
 
#### 效果图展示 
![项目效果图](https://upload-images.jianshu.io/upload_images/9305757-818106225dd01e65.gif?imageMogr2/auto-orient/strip)
 
#### APK下载：

- [Github下载](https://github.com/hegaojian/JetpackMvvm/releases/download/1.0.1/app-release.apk)

- [firm下载(推荐)](http://d.6short.com/v9q7)

- 扫码下载(推荐)

![](https://upload-images.jianshu.io/upload_images/9305757-4999111e26d5e93a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
  
  
## 1.如何集成

- **1.1 在root's build.gradle中加入Jitpack仓库**

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- **1.2 在app's build.gradle中添加依赖**

```
dependencies {
  ...
  implementation 'me.hegj:JetpackMvvm:1.0.7'
}
```

- **1.3 在app's build.gradle中，android 模块下开启DataBinding(如果你不想用DataBinding,请忽略这一步)**

```
android {
    ...
    dataBinding {
        enabled = true
    }
}
```

## 2.继承基类
一般我们项目中都会有一套自己定义的符合业务需求的基类 ***BaseActivity/BaseFragment***，所以我们的基类需要**继承本框架的Base类**
- **2.1 如果你开启了DataBinding 那么基类可以继承BaseVmDbActivity/BaseVmDbFragment**  

**Activity：**

```
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {
     /**
     * 当前Activity绑定的视图布局Id abstract修饰供子类实现
     */
    abstract override fun layoutId(): Int
    /**
     * 当前Activityc创建后调用的方法 abstract修饰供子类实现
     */
    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData数据观察 abstract修饰供子类实现
     */
    abstract override fun createObserver()


    /**
     * 打开等待框 在这里实现你的等待框展示
     */
    override fun showLoading(message: String) {
       ...
    }

    /**
     * 关闭等待框 在这里实现你的等待框关闭
     */
    override fun dismissLoading() {
       ...
    }
}
```
**Fragment：**
```
abstract class BaseFragment<VM : BaseViewModel,DB:ViewDataBinding> : BaseVmDbFragment<VM,DB>() {
    /**
     * 当前Fragment绑定的视图布局Id abstract修饰供子类实现
     */
    abstract override fun layoutId(): Int
   
    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法 abstract修饰供子类实现
     */
    abstract override fun lazyLoadData()

    /**
     * 创建liveData数据观察 懒加载之后才会触发 abstract修饰供子类实现
     */
    abstract override fun createObserver()
  
    /**
     * Fragment执行onViewCreated后触发的方法 
     */
    override fun initData() {

    }
    
   /**
     * 打开等待框 在这里实现你的等待框展示
     */
    override fun showLoading(message: String) {
       ...
    }

    /**
     * 关闭等待框 在这里实现你的等待框关闭
     */
    override fun dismissLoading() {
       ...
    }
}
```

- **2.2 如果你没有开启DataBinding 那么基类可以继承 BaseVmActivity/BaseVmFragment**

**Activity：**

```
abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
    
    abstract override fun layoutId(): Int
    
    abstract override fun initView(savedInstanceState: Bundle?)
    
    /**
     * 创建liveData数据观察 abstract修饰供子类实现
     */
    abstract override fun createObserver()


    /**
     * 打开等待框 在这里实现你的等待框展示
     */
    override fun showLoading(message: String) {
       ...
    }

    /**
     * 关闭等待框 在这里实现你的等待框关闭
     */
    override fun dismissLoading() {
       ...
    }
}
```
**Fragment:**
```
abstract class BaseFragment<VM : BaseViewModel> : BaseVmDbFragment<VM>() {
    /**
     * 当前Fragment绑定的视图布局Id abstract修饰供子类实现
     */
    abstract override fun layoutId(): Int
    
    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法 abstract修饰供子类实现
     */
    abstract override fun lazyLoadData()

    /**
     * 创建liveData数据观察 懒加载之后才会触发
     */
    abstract override fun createObserver()
  
    /**
     * Fragment执行onViewCreated后触发的方法
     */
    override fun initData() {

    }
    
   /**
     * 打开等待框 在这里实现你的等待框展示
     */
    override fun showLoading(message: String) {
       ...
    }

    /**
     * 关闭等待框 在这里实现你的等待框关闭
     */
    override fun dismissLoading() {
       ...
    }
}
```
## 3.创建一个Fragment（开启了DataBinding）

- **3.1 编写fragment_login.xml界面后转换成 databind 布局（鼠标停在根布局，Alt+Enter 点击提示 Convert to data binding layout即可）**
```
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/tools">
    <data>
       
    </data>
    <LinearLayout>
       ....
    </LinearLayout>
 </layout>   
```
- **3.2 创建LoginViewModel类继承BaseViewModel**

```
class LoginViewModel : BaseViewModel() {
  
}
```

- **3.3 LoginFragment继承基类传入相关泛型,第一个泛型为你创建的LoginViewModel,第二个泛型为ViewDataBind，保存fragment_login.xml后databinding会生成一个FragmentLoginBinding类。（如果没有生成，试着点击Build->Clean Project）**
```
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
  
    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        ...
    }
    
    override fun lazyLoadData() { 
        ...
    }
    
    override fun createObserver() {
        ...
    }
    
}
```

## 4.网络请求（Retrofit+协程）

- **4.1 新建请求配置类继承 BaseNetworkApi 示例：**
```
class NetworkApi : BaseNetworkApi() {
    //封装NetApiService变量 方便直接快速调用
    val service: NetApiService by lazy {
        getApi(NetApiService::class.java, NetApiService.SERVER_URL)
    }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(App.CONTEXT.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(HeadInterceptor(mapOf()))
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }
    }

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.CONTEXT))
    }

}
```


- **4.2如果你请求服务器返回的数据有基类（没有可忽略这一步）例如:**
```
{
    "data": ...,
    "errorCode": 0,
    "errorMsg": ""
}
```
该格式是 [玩Android Api](ttps://www.wanandroid.com/blog/show/2)返回的数据格式，如果errorCode等于0 请求成功，否则请求失败
作为开发者的角度来说，我们主要是想得到脱壳数据-data，且不想每次都判断errorCode==0请求是否成功或失败
这时我们可以在服务器返回数据基类中继承BaseResponse，实现相关方法：

```
data class ApiResponse<T>(var errorCode: Int, var errorMsg: String, var data: T) : BaseResponse<T>() {

    // 这里是示例，wanandroid 网站返回的 错误码为 0 就代表请求成功，请你根据自己的业务需求来编写
    override fun isSucces() = errorCode == 0

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg

}
```
- **4.3 在Viewmodel中发起请求，所有请求都是在viewModelScope中启动，请求会发生在IO线程，最终回调在主线程上，当页面销毁的时候，请求会统一取消，不用担心内存泄露的风险，框架做了2种请求使用方式**  

**1-2将请求数据包装给ResultState，在Activity/Fragment中去监听ResultState拿到数据做处理**

**3-4直接在当前ViewMdel中拿到请求结果做处理**

```
class LoginViewModel : BaseViewModel() {
   //1  自动脱壳过滤处理请求结果，判断结果是否成功
    var loginResult = MutableLiveData<ResultState<UserInfo>>()
    //2  不用框架帮脱壳，判断结果是否成功
    var loginResult2 = MutableLiveData<ResultState<ApiResponse<UserInfo>>>()
 fun login() {
      //1.这种是在 Activity/Fragment的监听回调中拿到已脱壳的数据（项目有基类的可以用）
       request(
            { NetworkApi().service.login(用户名, 密码) }//请求体
            , loginResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "正在登录中..."//等待框内容，可以默认不填请求网络中...
        )

       //2.这种是在Activity/Fragment中的监听拿到未脱壳的数据，你可以自己根据code做业务需求操作
        requestNoCheck({ NetworkApi().service.login(用户名, 密码) },loginResult2,true,正在登录中...")

       //3.这种是直接在当前Viewmodel中就拿到了脱壳数据数据 如果项目有基类的可以用
       request({ NetworkApi().service.login(用户名, 密码) },{
            //请求成功 已自动处理了 请求结果是否正常
        },{
            //请求失败 网络异常，或者请求结果码错误都会回调在这里
        },true,正在登录中...")

        //4.这种是直接在当前Viewmodel中就拿到了未脱壳数据数据
        requestNoCheck({ NetworkApi().service.login(用户名, 密码) },{
            //请求成功 自己拿到数据做业务需求操作
            if(it.errorCode==0){
                //结果正确
            }else{
                //结果错误
            }
        },{
            //请求失败 网络异常回调在这里
        },true,正在登录中...")
 }
}
方式1-2 在LoginFragment中监听：
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
  
    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        ...
    }
    
    override fun lazyLoadData() { 
        ...
    }
    
    override fun createObserver() {
        //监听请求结果
        mViewModel.loginResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                //登录成功
            }, {
                //请求失败 网络异常，或者请求结果码错误都会回调在这里
            })
        })
        mViewModel.loginResult2.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState,{
                //请求成功 自己拿到数据做业务需求操作
                if(it.errorCode==0){
                    //登录成功
                }else{
                    //登录失败
                }
            },{
                //请求失败异常
            })
        })
    }
}
```

## License
```
 Copyright 2019, hegaojian(何高建)       
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```


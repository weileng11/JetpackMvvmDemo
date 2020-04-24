package me.hgj.jetpackmvvm.demo.ui.login

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.BaseViewModel
import me.hgj.jetpackmvvm.databind.BooleanObservableField
import me.hgj.jetpackmvvm.databind.StringObservableField
import me.hgj.jetpackmvvm.demo.app.network.NetworkApi
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.UpdateUiState
import me.hgj.jetpackmvvm.demo.data.ApiResponse
import me.hgj.jetpackmvvm.demo.data.bean.UserInfo
import me.hgj.jetpackmvvm.demo.data.repository.LoginRepository
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.state.ResultState

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　:登录注册的Viewmodel
 */
class LoginRegisterViewModel : BaseViewModel() {

    //用户名
    var username = StringObservableField()

    //密码(登录注册界面)
    var password = StringObservableField()
    var password2 = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()
    var isShowPwd2 = BooleanObservableField()

    private val loginRpository: LoginRepository by lazy { LoginRepository() }
    //方式1  自动脱壳过滤处理请求结果，判断结果是否成功
    var loginResult = MutableLiveData<ResultState<UserInfo>>()
    //方式2  不用框架帮脱壳，判断结果是否成功
//    var loginResult2 = MutableLiveData<ResultState<ApiResponse<UserInfo>>>()

    fun loginReq() {
        //1.这种是在 Activity/Fragment的监听回调中拿到已脱壳的数据（项目有基类的可以用）
       request(
            { NetworkApi().service.login(username.get(), password.get()) }//请求体
            , loginResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "正在登录中..."//等待框内容，可以默认不填请求网络中...
        )

        //2.这种是在Activity/Fragment中的监听拿到未脱壳的数据，你可以自己根据code做业务需求操作（项目没有基类的可以用）
        /*requestNoCheck({loginRpository.login(username.get(),password.get())},loginResult2,true)*/

        //3. 这种是直接在当前Viewmodel中就拿到了脱壳数据数据，做一层封装再给Activity/Fragment，如果 （项目有基类的可以用）
       /* request({loginRpository.login(username.get(),password.get())},{
            //请求成功 已自动处理了 请求结果是否正常
        },{
            //请求失败 网络异常，或者请求结果码错误都会回调在这里
        })*/

        //4.这种是直接在当前Viewmodel中就拿到了未脱壳数据数据，（项目没有基类的可以用）
        /*requestNoCheck({loginRpository.login(username.get(),password.get())},{
            //请求成功 自己拿到数据做业务需求操作
            if(it.errorCode==0){
                //结果正确
            }else{
                //结果错误
            }
        },{
            //请求失败 网络异常回调在这里
        })*/
    }

    fun registerAndlogin() {
        request(
            { loginRpository.register(username.get(), password.get()) }
            , loginResult,
            true,
            "正在注册中..."
        )
    }
}
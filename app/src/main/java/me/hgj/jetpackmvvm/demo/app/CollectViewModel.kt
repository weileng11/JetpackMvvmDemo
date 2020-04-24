package me.hgj.jetpackmvvm.demo.app

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.CollectUiState
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.demo.data.bean.CollectResponse
import me.hgj.jetpackmvvm.demo.data.bean.CollectUrlResponse
import me.hgj.jetpackmvvm.demo.data.repository.CollectRepository
import me.hgj.jetpackmvvm.ext.request

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/3
 * 描述　: 专门为了收藏而写的Viewmodel，因为玩安卓很多界面都有收藏的功能，所有普通的Viewmodel
 * 需要收藏功能的话就可以继承它拥有收藏功能
 */
open class CollectViewModel : BaseViewModel() {
    private  var pageNo = 0
    private val collectRepositiory: CollectRepository by lazy { CollectRepository() }
    //收藏文章
    val collectUiState: MutableLiveData<CollectUiState> = MutableLiveData()
    //收藏网址
    val collectUrlUiState: MutableLiveData<CollectUiState> = MutableLiveData()
    //收藏de文章数据
    var ariticleDataState: MutableLiveData<ListDataUiState<CollectResponse>> = MutableLiveData()
    //收藏de网址数据
    var urlDataState: MutableLiveData<ListDataUiState<CollectUrlResponse>> = MutableLiveData()

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun collect(id: Int) {
        request({ collectRepositiory.collect(id) }, {
            val uiState = CollectUiState(isSuccess = true, collect = true, id = id)
            collectUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(isSuccess = false, collect = false, errorMsg = it.errorMsg, id = id)
            collectUiState.postValue(uiState)
        })
    }

    /**
     * 取消收藏文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun uncollect(id: Int) {
        request({ collectRepositiory.uncollect(id) }, {
            val uiState = CollectUiState(isSuccess = true, collect = false, id = id)
            collectUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(isSuccess = false, collect = true, errorMsg = it.errorMsg, id = id)
            collectUiState.postValue(uiState)
        })
    }

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun collectUrl(name: String,link:String) {
        request({ collectRepositiory.collectUrl(name,link) }, {
            val uiState = CollectUiState(isSuccess = true, collect = true, id = it.id)
            collectUrlUiState.postValue(uiState)
        }, {
            val uiState =  CollectUiState(isSuccess = false, collect = false, errorMsg = it.errorMsg, id = 0)
            collectUrlUiState.postValue(uiState)
        })
    }

    /**
     * 取消收藏网址
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun uncollectUrl(id: Int) {
        request({ collectRepositiory.uncollectUrl(id) }, {
            val uiState = CollectUiState(isSuccess = true, collect = false, id = id)
            collectUrlUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(isSuccess = false, collect = true, errorMsg = it.errorMsg, id = id)
            collectUrlUiState.postValue(uiState)
        })
    }

    fun getCollectAriticleData(isRefresh:Boolean){
        if (isRefresh) {
            pageNo = 0
        }
        request({ collectRepositiory.collectAriticleData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            ariticleDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<CollectResponse>()
                )
            ariticleDataState.postValue(listDataUiState)
        })
    }

    fun getCollectUrlData(){
        request({ collectRepositiory.collectUrlData() }, {
            //请求成功
            val listDataUiState =
                ListDataUiState(
                    isRefresh = true,
                    isSuccess = true,
                    hasMore = false,
                    isEmpty = it.isEmpty(),
                    listData = it
                )
            urlDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    listData = arrayListOf<CollectUrlResponse>()
                )
            urlDataState.postValue(listDataUiState)
        })
    }



}
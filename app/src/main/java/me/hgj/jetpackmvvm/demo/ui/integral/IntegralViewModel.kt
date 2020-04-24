package me.hgj.jetpackmvvm.demo.ui.integral

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.demo.data.bean.IntegralHistoryResponse
import me.hgj.jetpackmvvm.demo.data.bean.IntegralResponse
import me.hgj.jetpackmvvm.demo.data.repository.IntegralRepository
import me.hgj.jetpackmvvm.ext.request

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/10
 * 描述　:
 */
class IntegralViewModel :BaseViewModel() {

    private var pageNo = 1
    //积分排行数据
    var integralDataState = MutableLiveData<ListDataUiState<IntegralResponse>>()
    //获取积分历史数据
    var integralHistoryDataState = MutableLiveData<ListDataUiState<IntegralHistoryResponse>>()

    var rank = ObservableField<IntegralResponse>()

    private  val repository :IntegralRepository by lazy { IntegralRepository() }

    fun getIntegralData(isRefresh:Boolean){
        if(isRefresh){
            pageNo = 1
        }
        request({repository.getIntegralData(pageNo)},{
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
            integralDataState.postValue(listDataUiState)
        },{
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralResponse>()
                )
            integralDataState.postValue(listDataUiState)
        })
    }

    fun getIntegralHistoryData(isRefresh:Boolean){
        if(isRefresh){
            pageNo = 1
        }
        request({repository.getIntegralHistoryData(pageNo)},{
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
            integralHistoryDataState.postValue(listDataUiState)
        },{
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralHistoryResponse>()
                )
            integralHistoryDataState.postValue(listDataUiState)
        })
    }
}
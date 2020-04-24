package me.hgj.jetpackmvvm.demo.ui.publicNumber

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.state.ResultState
import me.hgj.jetpackmvvm.demo.app.CollectViewModel
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.demo.data.bean.AriticleResponse
import me.hgj.jetpackmvvm.demo.data.bean.ClassifyResponse
import me.hgj.jetpackmvvm.demo.data.repository.PublicRepository
import me.hgj.jetpackmvvm.ext.request

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/29
 * 描述　:
 */
class PublicNumberViewModel : CollectViewModel() {

    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    var publicDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    private val repository:PublicRepository by lazy { PublicRepository() }

    fun getPublicTitleData() {
        request({ repository.getTitleData() }, titleData)
    }

    fun getPublicData(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ repository.getPublicData(pageNo, cid) },{
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
            publicDataState.postValue(listDataUiState)
        },{
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            publicDataState.postValue(listDataUiState)
        })
    }
}
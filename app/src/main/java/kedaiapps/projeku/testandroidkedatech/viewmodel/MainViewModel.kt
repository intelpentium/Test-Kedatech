package kedaiapps.projeku.testandroidkedatech.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kedaiapps.projeku.testandroidkedatech.BuildConfig
import kedaiapps.projeku.testandroidkedatech.common.ActionLiveData
import kedaiapps.projeku.testandroidkedatech.common.UiState
import kedaiapps.projeku.testandroidkedatech.ext.errorMesssage
import kedaiapps.projeku.testandroidkedatech.services.entity.ResponseHome
import kedaiapps.projeku.testandroidkedatech.services.entity.ResponseHomeDetail
import kedaiapps.projeku.testandroidkedatech.services.rest.MainRest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRest: MainRest
) : ViewModel() {

    val loadState = ActionLiveData<UiState>()

    val responseHome = ActionLiveData<List<ResponseHome>>()
    val responsePagination = ActionLiveData<List<ResponseHome>>()
    val responseSearch = ActionLiveData<List<ResponseHome>>()
    val responseHomeDetail = ActionLiveData<ResponseHomeDetail>()

    fun home() {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.home(BuildConfig.API_KEY)
                responseHome.value = response.results!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun paggination(page: Int, pageSize: Int, search: String) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.paggination(page, pageSize, search, BuildConfig.API_KEY)
                responsePagination.value = response.results!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun search(data: String) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.search(data, BuildConfig.API_KEY)
                responseSearch.value = response.results!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }

    fun homeDetail(id: String) {
        viewModelScope.launch {
            loadState.sendAction(UiState.Loading)
            try {
                val response = mainRest.homeDetail(id, BuildConfig.API_KEY)
                responseHomeDetail.value = response!!
                loadState.sendAction(UiState.Success)
            } catch (e: Exception) {
                loadState.sendAction(UiState.Error(e.errorMesssage))
            }
        }
    }
}
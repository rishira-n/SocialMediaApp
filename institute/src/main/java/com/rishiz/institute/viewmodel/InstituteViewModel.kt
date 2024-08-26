package com.rishiz.institute.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishiz.institute.data.remote.response.ApiResponse
import com.rishiz.institute.data.remote.response.NetworkResponse
import com.rishiz.institute.domain.model.SocialMediaLinkReq
import com.rishiz.institute.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstituteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _saveState: MutableStateFlow<NetworkResponse<Unit>> =
        MutableStateFlow(NetworkResponse.Empty)
    val saveState: StateFlow<NetworkResponse<Unit>> = _saveState.asStateFlow()
    private var _updateState: MutableStateFlow<NetworkResponse<Unit>> =
        MutableStateFlow(NetworkResponse.Empty)
    val updateState: StateFlow<NetworkResponse<Unit>> = _updateState.asStateFlow()
    private var _deleteState: MutableStateFlow<NetworkResponse<ApiResponse>> =
        MutableStateFlow(NetworkResponse.Empty)
    val deleteState: StateFlow<NetworkResponse<ApiResponse>> = _deleteState.asStateFlow()

    fun saveLinkApi(req: SocialMediaLinkReq) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.socialMediaLinkSaveApi(req)
                .collectLatest { res ->
                    _saveState.value = res
                }
        }
    }

    fun updateLinkApi(req: SocialMediaLinkReq) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSocialMediaLinkApi(req)
                .collectLatest { res ->
                    _updateState.value = res
                }
        }
    }

    fun deleteLinkApi(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSocialMediaLinkApi(userId)
                .collectLatest { res ->
                    _deleteState.value = res
                }
        }
    }

}
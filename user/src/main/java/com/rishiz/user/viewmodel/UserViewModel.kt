package com.rishiz.user.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishiz.user.data.remote.response.NetworkResponse
import com.rishiz.user.domain.model.SocialMediaLinkRes
import com.rishiz.user.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _linkState: MutableStateFlow<NetworkResponse<SocialMediaLinkRes>> =
        MutableStateFlow(NetworkResponse.Empty)
    val linkState: StateFlow<NetworkResponse<SocialMediaLinkRes>> = _linkState.asStateFlow()


    fun getAllLinks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSocialMediaLinkApi()
                .collectLatest { res ->
                    _linkState.value = res
                }
        }
    }

}
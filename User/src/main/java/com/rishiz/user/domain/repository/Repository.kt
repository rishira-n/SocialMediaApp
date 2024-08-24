package com.rishiz.user.domain.repository

import com.rishiz.user.data.remote.api.ApiService
import com.rishiz.user.data.remote.response.NetworkResponse
import com.rishiz.user.domain.model.SocialMediaLinkRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService,
) {

    suspend fun getSocialMediaLinkApi(): Flow<NetworkResponse<SocialMediaLinkRes>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = api.getAllLinks()

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResponse.Success(response.body()!!))
            } else {
                //this will navigate to catch block
                throw Exception()
            }
        }.catch { e ->
            /*
            it is giving null pointer exception error
            emit(BaseResponseState.Error(AppUtil.returnErrorMsg( context,e)!!))
            */
            e.localizedMessage?.let { NetworkResponse.Error(it) }?.let { emit(it) }
        }
    }
}
package com.rishiz.institute.domain.repository

import com.rishiz.institute.data.remote.api.ApiService
import com.rishiz.institute.data.remote.response.NetworkResponse
import com.rishiz.institute.domain.model.SocialMediaLinkReq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService,
) {

    suspend fun socialMediaLinkSaveApi(req: SocialMediaLinkReq): Flow<NetworkResponse<Unit>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = api.saveSocialMediaLink(req)

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResponse.Success(response.body()!!))
            } else {
                //this will navigate to catch block
                emit(NetworkResponse.Error(response.message()))
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
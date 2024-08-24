package com.rishiz.institute.domain.repository

import com.rishiz.institute.data.remote.api.ApiService
import com.rishiz.institute.data.remote.response.ApiResponse
import com.rishiz.institute.data.remote.response.NetworkResponse
import com.rishiz.institute.domain.model.SocialMediaLinkReq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
                emit(NetworkResponse.Error(response.code().toString() + ":" + response.message()))
            }
        }.catch { e ->
            e.localizedMessage?.let { NetworkResponse.Error(it) }?.let { emit(it) }
        }
    }

    suspend fun updateSocialMediaLinkApi(req: SocialMediaLinkReq): Flow<NetworkResponse<Unit>> {
        return flow {
            emit(NetworkResponse.Loading)
            // Prepare RequestBody instances
//            val platformBody = req.platform.toRequestBody("text/plain".toMediaTypeOrNull())
//            val urlBody = req.url.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.updateSocialMediaLink(userId = req.userId,req)

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResponse.Success(response.body()!!))
            } else {
                emit(NetworkResponse.Error(response.code().toString() + ":" + response.message()))
                throw Exception()
            }
        }.catch { e ->
            e.localizedMessage?.let { NetworkResponse.Error(it) }?.let { emit(it) }
        }
    }

    suspend fun deleteSocialMediaLinkApi(userId: String): Flow<NetworkResponse<ApiResponse>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = api.deleteSocialMediaLink(userId)

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResponse.Success(response.body()!!))
            } else {
                emit(NetworkResponse.Error(response.code().toString() + ":" + response.message()))

            }
        }.catch { e ->

            e.localizedMessage?.let { NetworkResponse.Error(it) }?.let { emit(it) }
        }
    }
}
package com.rishiz.institute.data.remote.api

import com.rishiz.institute.data.remote.response.ApiResponse
import com.rishiz.institute.domain.model.SocialMediaLinkReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("social-media/save")
    suspend fun saveSocialMediaLink(@Body requestBody: SocialMediaLinkReq): Response<Unit>

    @POST("social-media/update/{userId}")
    suspend fun updateSocialMediaLink(
        @Path("userId") userId: String,
        @Body requestBody: SocialMediaLinkReq
    ): Response<Unit>

    @POST("social-media/delete/{userId}")
    suspend fun deleteSocialMediaLink(@Path("userId") userId: String): Response<ApiResponse>

}
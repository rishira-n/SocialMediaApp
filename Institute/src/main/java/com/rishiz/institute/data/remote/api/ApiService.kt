package com.rishiz.institute.data.remote.api

import com.rishiz.institute.domain.model.SocialMediaLinkReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("social-media/save")
    suspend fun saveSocialMediaLink(@Body requestBody: SocialMediaLinkReq): Response<Unit>
}
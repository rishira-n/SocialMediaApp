package com.rishiz.user.data.remote.api

import com.rishiz.user.domain.model.SocialMediaLinkRes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("social-media/all-links")
    suspend fun getAllLinks(): Response<SocialMediaLinkRes>
}
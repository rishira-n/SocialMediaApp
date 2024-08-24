package com.rishiz.institute.domain.model

data class SocialMediaLinkReq(
    val userId: String,
    val platform: String,
    val url: String,
    val icon: String
)

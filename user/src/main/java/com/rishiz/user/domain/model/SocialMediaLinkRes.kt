package com.rishiz.user.domain.model

data class SocialMediaLinkRes(
    val statusCode: Int,
    val message: String,
    val data: List<SocialMediaLink>?
)

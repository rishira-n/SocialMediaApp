package com.rishiz.user.domain.model

data class SocialMediaLinkRes(
    val success: Boolean,
    val message: String,
    val data: List<SocialMediaLink>?
)

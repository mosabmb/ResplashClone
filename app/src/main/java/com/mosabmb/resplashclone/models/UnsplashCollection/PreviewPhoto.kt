package com.mosabmb.resplashclone.models.UnsplashCollection

import java.io.Serializable

data class PreviewPhoto(
    val blur_hash: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val urls: UrlsX
) : Serializable
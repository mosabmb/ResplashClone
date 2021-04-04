package com.mosabmb.resplashclone.models.UnsplashCollection

import java.io.Serializable

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Serializable
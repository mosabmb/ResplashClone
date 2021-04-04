package com.mosabmb.resplashclone.models.UnsplashCollection

import java.io.Serializable

data class Tag(
    val source: Source,
    val title: String,
    val type: String
) : Serializable
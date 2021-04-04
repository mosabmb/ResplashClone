package com.mosabmb.resplashclone.models.UnsplashCollection

import java.io.Serializable

data class Category(
    val pretty_slug: String,
    val slug: String
) : Serializable
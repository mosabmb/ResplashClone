package com.mosabmb.resplashclone.api.Responses

import com.mosabmb.resplashclone.models.UnsplashPhoto.UnsplashPhoto

data class UnsplashPhotosSearchResponse (
        val results: List<UnsplashPhoto>
)
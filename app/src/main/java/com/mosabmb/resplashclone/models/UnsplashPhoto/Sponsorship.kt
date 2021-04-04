package com.mosabmb.resplashclone.models.UnsplashPhoto

import java.io.Serializable

data class Sponsorship(
    val impression_urls: List<Any>,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
) : Serializable
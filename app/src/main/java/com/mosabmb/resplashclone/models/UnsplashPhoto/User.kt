package com.mosabmb.resplashclone.models.UnsplashPhoto

import java.io.Serializable

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: Any,
    val last_name: String,
    val links: LinksXX,
    val location: Any,
    val name: String,
    val portfolio_url: Any,
    val profile_image: ProfileImageX,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: Any,
    val updated_at: String,
    val username: String
) : Serializable
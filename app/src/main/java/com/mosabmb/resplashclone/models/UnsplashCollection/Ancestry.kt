package com.mosabmb.resplashclone.models.UnsplashCollection

import java.io.Serializable

data class Ancestry(
    val category: Category,
    val subcategory: Subcategory,
    val type: Type
) : Serializable
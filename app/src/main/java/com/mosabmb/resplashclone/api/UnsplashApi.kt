package com.mosabmb.resplashclone.api

import com.mosabmb.resplashclone.BuildConfig
import com.mosabmb.resplashclone.api.Responses.UnsplashCollectionsResponse
import com.mosabmb.resplashclone.api.Responses.UnsplashPhotosResponse
import com.mosabmb.resplashclone.api.Responses.UnsplashPhotosSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com"
        const val CLIENT_ID = BuildConfig.API_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") pageNumber: Int,
        @Query("order_by") orderBy: String = "latest",
    ) : UnsplashPhotosResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("/collections")
    suspend fun getCollections(
            @Query("page") page: Int,
            @Query("per_page") pageNumber: Int,
    ) : UnsplashCollectionsResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhotos(
            @Path("id") id: String,
            @Query("page") page: Int,
            @Query("per_page") pageNumber: Int,
    ) : UnsplashPhotosResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
    ): UnsplashPhotosSearchResponse



}
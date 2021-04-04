package com.mosabmb.resplashclone.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mosabmb.resplashclone.api.*
import com.mosabmb.resplashclone.models.UnsplashCollection.UnsplashCollection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor( private val unsplashApi: UnsplashApi ) {


    fun getPhotos() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi) }
        ).liveData


    fun getCollections() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashCollectionsPagingSource(unsplashApi) }
        ).liveData


    fun getCollectionPhotos(collection : UnsplashCollection) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashCollectionPhotosPagingSource(unsplashApi, collection) }
        ).liveData

    fun getSearchResults(query: String) =
            Pager(
                    config = PagingConfig(
                            pageSize = 20,
                            maxSize = 100,
                            enablePlaceholders = false
                    ),
                    pagingSourceFactory = { UnsplashSearchPhotosPagingSource(unsplashApi, query) }
            ).liveData


}
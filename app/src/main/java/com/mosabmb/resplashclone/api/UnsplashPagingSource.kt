package com.mosabmb.resplashclone.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.mosabmb.resplashclone.models.UnsplashCollection.UnsplashCollection
import com.mosabmb.resplashclone.models.UnsplashPhoto.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi

) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.getPhotos(position, params.loadSize)
            val photos = response

            LoadResult.Page(
                data = photos,
                prevKey = if ( position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if ( photos.isEmpty()) null else position - 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}

class UnsplashCollectionsPagingSource(
    private val unsplashApi: UnsplashApi
) : PagingSource<Int, UnsplashCollection>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashCollection> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.getCollections(position, params.loadSize)
            val collections = response

            LoadResult.Page(
                data = collections,
                prevKey = if ( position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if ( collections.isEmpty()) null else position - 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}

class UnsplashCollectionPhotosPagingSource(
        private val unsplashApi: UnsplashApi,
        private val collection : UnsplashCollection
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.getCollectionPhotos(collection.id ,position, params.loadSize)
            val photos = response

            LoadResult.Page(
                data = photos,
                prevKey = if ( position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if ( photos.isEmpty()) null else position - 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}

class UnsplashSearchPhotosPagingSource(
        private val unsplashApi: UnsplashApi,
        private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                    data = photos,
                    prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
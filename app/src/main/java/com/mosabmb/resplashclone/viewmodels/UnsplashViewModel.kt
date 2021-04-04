package com.mosabmb.resplashclone.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mosabmb.resplashclone.models.UnsplashCollection.UnsplashCollection
import com.mosabmb.resplashclone.models.UnsplashPhoto.UnsplashPhoto
import com.mosabmb.resplashclone.repositories.UnsplashRepository
import kotlinx.coroutines.launch

class UnsplashViewModel @ViewModelInject constructor(
    private val unsplashRespository: UnsplashRepository
) : ViewModel() {

    val photos = unsplashRespository.getPhotos().cachedIn(viewModelScope)
    val collections = unsplashRespository.getCollections().cachedIn(viewModelScope)
    fun getCollectionPhotos(collection : UnsplashCollection) = unsplashRespository.getCollectionPhotos(collection)

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val searchPhotos = currentQuery.switchMap { queryString ->
        unsplashRespository.getSearchResults(queryString).cachedIn(viewModelScope)
    }
    fun searchPhotos(query: String) {
        currentQuery.value = query
    }
    companion object {
        private const val DEFAULT_QUERY = "cats"
    }

}
package com.alphasoftcu.apiex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphasoftcu.apiex.model.ApiMediator
import com.alphasoftcu.apiex.model.PostModel

/**
 * ViewModel for provide data to PostsFragment UI
 */
class PostsViewModel(application: Application): AndroidViewModel(application) {

    private var apiMediator: ApiMediator ?= null

    /**
     * Post list LiveData
     */
    var postModelListLiveData: LiveData<List<PostModel>?> ?= null

    init {
        apiMediator = ApiMediator()
        postModelListLiveData = MutableLiveData()
    }

    /**
     * Get Posts list data throw API request for [postModelListLiveData]
     */
    fun getAllPosts(){
        postModelListLiveData = apiMediator?.getAllPosts()
    }

}
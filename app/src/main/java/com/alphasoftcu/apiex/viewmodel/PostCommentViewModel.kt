package com.alphasoftcu.apiex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphasoftcu.apiex.model.ApiMediator
import com.alphasoftcu.apiex.model.CommentModel
import com.alphasoftcu.apiex.model.PostModel
import com.alphasoftcu.apiex.model.UserModel

/**
 * ViewModel for Post's detail activity. Provide data for PostDetailsActivity UI.
 */
class PostCommentViewModel(application: Application): AndroidViewModel(application) {

    private var apiMediator: ApiMediator?= null

    /**
     * Post LiveData
     */
    var postModelLiveData: LiveData<PostModel?>?= null

    /**
     * User LiveData
     */
    var userModelLiveData: LiveData<UserModel?>?= null

    /**
     * Comments list LiveData
     */
    var commentModelLiveData: LiveData<List<CommentModel>?>?= null

    init {
        apiMediator = ApiMediator()
        postModelLiveData = MutableLiveData()
        userModelLiveData = MutableLiveData()
        commentModelLiveData = MutableLiveData()
    }

    /**
     * Get User data throw API request for [userModelLiveData]
     * @param userId User identifier
     */
    fun getUser(userId: Int){
        userModelLiveData = apiMediator?.getUser(userId)
    }

    /**
     * Get Post data throw API request for [postModelLiveData]
     * @param postId Post identifier
     */
    fun getPost(postId: Int){
        postModelLiveData = apiMediator?.getPost(postId)
    }

    /**
     * Get Comments list data throw API request for [commentModelLiveData]
     * @param postId Post's identifier for post parent of comments
     */
    fun getComments(postId: Int){
        commentModelLiveData = apiMediator?.getPostComments(postId)
    }

}
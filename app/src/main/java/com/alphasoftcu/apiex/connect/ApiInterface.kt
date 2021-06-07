package com.alphasoftcu.apiex.connect

import com.alphasoftcu.apiex.model.PostModel
import com.alphasoftcu.apiex.model.CommentModel
import com.alphasoftcu.apiex.model.UserModel

import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for declare functions to obtain data from API
 */
interface ApiInterface {

    /**
     * Function that get one user data from API
     * @param id User identifier
     * @return Callback object that implement Call interface with type UserModel
     */
    @GET("users/{id}")
    fun getUser(@Path("id") id:Int): Call<UserModel>

    /**
     * Function that get a collection if Posts data from API
     * @return Callback object that implement Call interface with type List<PostModel>
     */
    @GET("posts")
    fun getAllPosts(): Call<List<PostModel>>

    /**
     * Function that get one Post data from API
     * @param id Post identifier
     * @return Callback object that implement Call interface with type PostModel
     */
    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Call<PostModel>

    /**
     * Function that get a collection of Comments data from API for
     * one specific Post
     * @param postId Post identifier
     * @return Callback object that implement Call interface with type List<CommentModel>
     */
    @GET("posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId:Int): Call<List<CommentModel>>
}
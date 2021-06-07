package com.alphasoftcu.apiex.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alphasoftcu.apiex.connect.ApiClient
import com.alphasoftcu.apiex.connect.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiMediator {
    private var apiInterface:ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    /**
     * Get User by user Id from the REST API
     * @param id User ID
     * @return LiveData that hold a UserModel object
     */
    fun getUser(id: Int): LiveData<UserModel?>{
        val data = MutableLiveData<UserModel?>()

        apiInterface?.getUser(id)?.enqueue(object : Callback<UserModel>{
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

    /**
     * Get Post by post Id from the REST API
     * @param id Post ID
     * @return LiveData that hold a PostModel object
     */
    fun getPost(id: Int):LiveData<PostModel?>{
        val data = MutableLiveData<PostModel?>()

        apiInterface?.getPost(id)?.enqueue(object : Callback<PostModel>{
            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

    /**
     * Get Posts collection from the REST API
     * @return LiveData that hold a List of PostModel objects
     */
    fun getAllPosts(): LiveData<List<PostModel>?>{
        val data = MutableLiveData<List<PostModel>?>()

        apiInterface?.getAllPosts()?.enqueue(object : Callback<List<PostModel>>{

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

    /**
     * Get Comments collection of one Post from the REST API
     * @param postId Post ID
     * @return LiveData that hold a List of CommentModel objects
     */
    fun getPostComments(postId: Int): LiveData<List<CommentModel>?>{
        val data = MutableLiveData<List<CommentModel>?>()

        apiInterface?.getPostComments(postId)?.enqueue(object : Callback<List<CommentModel>>{

            override fun onFailure(call: Call<List<CommentModel>>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(call: Call<List<CommentModel>>, response: Response<List<CommentModel>>
            ) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }
}
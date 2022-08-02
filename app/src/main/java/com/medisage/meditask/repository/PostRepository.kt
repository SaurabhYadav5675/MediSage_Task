package com.medisage.meditask.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medisage.meditask.api.ApiService
import com.medisage.meditask.appDatabase.UserDatabase
import com.medisage.meditask.model.ApiPostModel
import com.medisage.meditask.model.Posts
import com.medisage.meditask.utilities.FieldValidation

class PostRepository(
    private val apiService: ApiService,
    private val userDatabase: UserDatabase,
    private val requireContext: Context
) {

    private val postLiveData = MutableLiveData<ApiPostModel>()
    val posts: LiveData<ApiPostModel>
        get() = postLiveData

    //data from database
    private val postLocalData = MutableLiveData<List<Posts>>()
    val postsLocal: LiveData<List<Posts>>
        get() = postLocalData

    //favourite data
    private val postFavLocalData = MutableLiveData<List<Posts>>()

    val postsFavLocal: LiveData<List<Posts>>
        get() = postFavLocalData

    suspend fun getPosts() {

        if (FieldValidation.isInternetAvailable(requireContext)) {
            val result = apiService.getPosts()
            if (result?.body() != null) {
                val apiItems = result.body()
                val dbItems = apiItems?.map { apiItems ->
                    Posts(
                        id = apiItems.id.toInt(),
                        userId = apiItems.userId.toInt(),
                        title = apiItems.title,
                        body = apiItems.body,
                        favourite = "0",
                    )
                }
                if (dbItems != null) {
                    userDatabase.postsDao().insertAllPost(dbItems)
                }
                postLiveData.postValue(result.body())
            }
        }
        val resultLocal = userDatabase.postsDao().getAllPost()
        if (resultLocal != null) {
            postLocalData.postValue(resultLocal)
        }
    }

    suspend fun updatePost(postId: Int, favValue: String) {
        userDatabase.postsDao().updateSinglePost(postId, favValue)
        val updatedData = userDatabase.postsDao().getAllPost()
        if (updatedData != null) {
            postLocalData.postValue(updatedData)
        }
        getFavPosts()
    }

    suspend fun getFavPosts() {
        val updatedData = userDatabase.postsDao().getAllFavPost()
        if (updatedData != null) {
            postFavLocalData.postValue(updatedData)
        }
    }


}
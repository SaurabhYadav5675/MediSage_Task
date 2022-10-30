package com.medisage.meditask.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medisage.meditask.api.ApiService
import com.medisage.meditask.appDatabase.UserDatabase
import com.medisage.meditask.model.Posts
import com.medisage.meditask.utilities.FieldValidation

class PostRepository(
    private val apiService: ApiService,
    private val userDatabase: UserDatabase,
    private val requireContext: Context
) {
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
            Log.e("Data133", "calling api")
            if (result.body() != null) {
                val apiItems = result.body()
                val dbItems = apiItems?.map { apiItems ->
                    Posts(
                        id = apiItems.id,
                        userId = apiItems.userId,
                        title = apiItems.title,
                        body = apiItems.body,
                        favourite = "0",
                    )
                }
                if (dbItems != null) {
                    with(postLocalData) { postValue(dbItems) }
                    userDatabase.postsDao().insertAllPost(dbItems)
                }
            }
        } else {
            Toast.makeText(requireContext, "getting data from database ", Toast.LENGTH_SHORT).show()
            val resultLocal = userDatabase.postsDao().getAllPost()
            if (resultLocal.isNotEmpty()) {
                postLocalData.postValue(resultLocal)
            }
        }
    }

    suspend fun updatePost(postId: Int, favValue: String) {
        userDatabase.postsDao().updateSinglePost(postId, favValue)
        val updatedData = userDatabase.postsDao().getAllPost()
        if (updatedData.isNotEmpty()) {
            postLocalData.postValue(updatedData)
        }
        getFavPosts()
    }

    fun getFavPosts() {
        val updatedData = userDatabase.postsDao().getAllFavPost()
        if (updatedData.isNotEmpty()) {
            postFavLocalData.postValue(updatedData)
        }
    }


}
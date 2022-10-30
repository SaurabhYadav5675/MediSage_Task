package com.medisage.meditask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medisage.meditask.model.Posts
import com.medisage.meditask.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    /*init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPosts()
        }
    }*/

    val postsLocal: LiveData<List<Posts>>
        get() = repository.postsLocal

    val postsFavLocal: LiveData<List<Posts>>
        get() = repository.postsFavLocal

    fun updatePostData(postId: Int, favStatus: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePost(postId, favStatus)
        }
    }
}

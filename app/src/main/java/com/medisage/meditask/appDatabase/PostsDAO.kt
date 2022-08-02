package com.medisage.meditask.appDatabase

import androidx.room.*
import com.medisage.meditask.model.Posts

@Dao
interface PostsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(posts: Posts)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPost(posts: List<Posts>)

    @Update
    suspend fun updatePost(posts: Posts)

    @Query("UPDATE posts SET favourite=:favStatus WHERE id=:postId")
    suspend fun updateSinglePost(postId: Int, favStatus: String)

    @Delete
    suspend fun deletePost(posts: Posts)

    @Query("SELECT * FROM posts")
    fun getAllPost(): List<Posts>

    @Query("SELECT * FROM posts where favourite like 1")
    fun getAllFavPost(): List<Posts>
}
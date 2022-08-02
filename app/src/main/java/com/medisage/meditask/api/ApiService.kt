package com.medisage.meditask.api

import com.medisage.meditask.model.ApiPostModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): Response<ApiPostModel>
}
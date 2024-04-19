package com.app.assignment.network

import com.app.assignment.models.ImageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos/")
    suspend fun getImages(
        @Query("client_id") clientId: String,
        @Query("per_page") perPage: String,
        @Query("page") page: String
    ): Response<List<ImageModel>>

}
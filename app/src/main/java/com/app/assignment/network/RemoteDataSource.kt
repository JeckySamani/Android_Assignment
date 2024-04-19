package com.app.assignment.network

import javax.inject.Inject

class RemoteDataSource  @Inject constructor(private val apiService: ApiService) {

    suspend fun getImages(clientId: String, perPage: String, page: String) =
        apiService.getImages(clientId, perPage, page)

}
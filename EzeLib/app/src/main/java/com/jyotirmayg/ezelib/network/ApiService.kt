package com.jyotirmayg.ezelib.network

import com.jyotirmayg.ezelib.data.apiModel.DynamicUiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("mobileapps/android_assignment.json")
    suspend fun fetchCustomUi(): Response<DynamicUiResponse>
}
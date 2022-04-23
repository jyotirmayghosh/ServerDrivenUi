package com.jyotirmayg.ezelib.data.repository

import com.jyotirmayg.ezelib.network.ApiService

class UiRepo(private val apiService: ApiService) : BaseRepo() {

    suspend fun getCustomUi() = safeApiCall {
        apiService.fetchCustomUi()
    }
}
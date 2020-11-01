package com.pradip.data


import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/")
    suspend fun getProfile(@Query("results") results: Int): JsonObject

}
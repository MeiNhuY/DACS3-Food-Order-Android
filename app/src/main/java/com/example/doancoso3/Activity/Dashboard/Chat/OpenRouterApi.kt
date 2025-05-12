package com.example.doancoso3.Activity.Dashboard.Chat

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Header

interface OpenRouterApi {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatResponse(
        @Body request: ChatRequest,
        @Header("Authorization") token: String,
        @Header("X-Title") title: String = "GobbleFood"
    ): ChatResponse
}





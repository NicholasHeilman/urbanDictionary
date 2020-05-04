package com.e.nikeurbanapp.repository.network

import com.e.nikeurbanapp.model.UrbanResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UrbanDictionaryService {
    @Headers(HEADER_RAPID_API_KEY)
    @GET("/define")

    suspend fun getDefinition(@Query("term") term: String): UrbanResponse

    companion object {
        private const val RAPID_API_KEY = "8942f51e5cmshfb94c6044dd0178p1b8b3cjsn86000671bb18"
        const val HEADER_RAPID_API_KEY = "X-RapidAPI-Key: $RAPID_API_KEY"
    }
}
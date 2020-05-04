package com.e.nikeurbanapp.repository

import com.e.nikeurbanapp.repository.network.RetrofitInstance

object Repository {
    suspend fun getDefinition(word: String) = RetrofitInstance.urbanService.getDefinition(word)
}
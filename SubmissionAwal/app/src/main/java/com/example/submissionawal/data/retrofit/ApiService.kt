package com.example.submissionawal.data.retrofit

import com.example.submissionawal.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") id: String
    ): Call<GithubResponse>
}
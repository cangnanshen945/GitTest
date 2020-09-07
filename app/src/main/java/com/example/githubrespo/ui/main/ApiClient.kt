package com.example.githubrespo.ui.main

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    interface ApiInterface {

        @GET("/users/cangcang/repos")
        fun loadMore(@Query("page") pageNum: Int): Call<List<ResponseItem>>
    }

    fun build(): ApiInterface {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.build()
        return retrofit.create(ApiInterface::class.java)
    }
}
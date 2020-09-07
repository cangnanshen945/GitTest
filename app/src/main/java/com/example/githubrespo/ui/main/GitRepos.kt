package com.example.githubrespo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitRepos {
    private var call: Call<List<ResponseItem>>? = null
    private var pageNum = 1
    fun getRepo(callback: NetCallBack) {
        pageNum = 1
        getRepoInternal(callback)
    }

    fun getRepoInternal(callback: NetCallBack) {
        call = ApiClient.build().loadMore(pageNum)
        call?.enqueue(object : Callback<List<ResponseItem>> {
            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<ResponseItem>>, response: Response<List<ResponseItem>>) {
                response.body()?.let {
                    callback.onResponse(it)
                    pageNum++
                }
            }

        })
    }

    fun loadMore(callback: NetCallBack) {
        getRepoInternal(callback)
    }

    fun cancel() {
        call?.cancel()
    }
}

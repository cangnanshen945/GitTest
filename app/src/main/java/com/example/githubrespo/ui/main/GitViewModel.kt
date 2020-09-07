package com.example.githubrespo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GitViewModel() : ViewModel() {
    private val repo = GitRepos()
    private val data: MutableList<ResponseItem> = mutableListOf()
    private val _repoList: MutableLiveData<List<ResponseItem>> = MutableLiveData(data)
    val repoList: LiveData<List<ResponseItem>> = _repoList
    private val _loadDataState: MutableLiveData<Int> = MutableLiveData(0)
    val loadDataState: LiveData<Int> = _loadDataState

    fun getRepo() {
        repo.getRepo(object : NetCallBack {
            override fun onResponse(list: List<ResponseItem>) {
                if (list.isEmpty()) {
                    _loadDataState.value = 1
                } else {
                    _loadDataState.value = 0
                }
                data.clear()
                data.addAll(list)
                _repoList.value = data
            }

        })
    }

    fun loadMore() {
        repo.loadMore(object : NetCallBack {
            override fun onResponse(list: List<ResponseItem>) {
                if (list.isEmpty()) {
                    _loadDataState.value = 2
                } else {
                    _loadDataState.value = 0
                    data.addAll(list)
                    _repoList.value = data
                }
            }

        })
    }
}
interface NetCallBack {
    fun onResponse(list: List<ResponseItem>)
}

package com.example.githubrespo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class GitViewModelFactory(): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GitViewModel() as T
    }
}
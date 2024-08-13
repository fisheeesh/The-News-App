package com.swamyiphyo.thenewsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swamyiphyo.thenewsapp.repository.NewsRepository

/**
 * ViewModelProviderFactory is basically instantiate and return view model
 */
@Suppress("UNCHECKED_CAST")
class NewsViewModelProviderFactory(private val app : Application, private val repo : NewsRepository) : ViewModelProvider.Factory {
    /**
     * This method is for to create an instance of view model class
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, repo) as T
    }
}
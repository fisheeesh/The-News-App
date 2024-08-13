package com.swamyiphyo.thenewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.swamyiphyo.thenewsapp.R
import com.swamyiphyo.thenewsapp.database.ArticleDatabase
import com.swamyiphyo.thenewsapp.databinding.ActivityNewsBinding
import com.swamyiphyo.thenewsapp.repository.NewsRepository
import com.swamyiphyo.thenewsapp.viewmodel.NewsViewModel
import com.swamyiphyo.thenewsapp.viewmodel.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel
    lateinit var binding : ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
    private fun setUpViewModel(){
        val newsRepo = NewsRepository(ArticleDatabase(this))
        val newsViewModelFactory = NewsViewModelProviderFactory(application, newsRepo)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory)[NewsViewModel::class.java]
    }
}
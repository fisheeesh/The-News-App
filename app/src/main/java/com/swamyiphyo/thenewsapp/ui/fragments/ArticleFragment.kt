package com.swamyiphyo.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.swamyiphyo.thenewsapp.R
import com.swamyiphyo.thenewsapp.databinding.FragmentArticleBinding
import com.swamyiphyo.thenewsapp.ui.NewsActivity
import com.swamyiphyo.thenewsapp.viewmodel.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {

    //All API responses and everything is connected in viewmodel
    private lateinit var newsViewModel : NewsViewModel
    private val args : ArticleFragmentArgs by navArgs()
    private lateinit var binding : FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fabFavorite.setOnClickListener(){
            newsViewModel.addToFavorites(article)
            Snackbar.make(view, "Added to Favorites!", Snackbar.LENGTH_SHORT).show()
        }

    }
}
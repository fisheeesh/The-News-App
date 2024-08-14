package com.swamyiphyo.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.swamyiphyo.thenewsapp.R
import com.swamyiphyo.thenewsapp.adapters.NewsAdapter
import com.swamyiphyo.thenewsapp.databinding.FragmentFavoriteBinding
import com.swamyiphyo.thenewsapp.ui.NewsActivity
import com.swamyiphyo.thenewsapp.viewmodel.NewsViewModel

class FavoriteFragment : Fragment() {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding : FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpFavoriteRecycler()

        navigateToArticle()

        swipeArticle(view)

        newsViewModel.getFavoriteNews().observe(viewLifecycleOwner){
            articles -> newsAdapter.differ.submitList(articles)
        }
    }

    private fun navigateToArticle(){
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(R.id.action_favoriteFragment_to_articleFragment, bundle)
        }
    }

    private fun swipeArticle(view : View){
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(view, "Remove from Favorites!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        newsViewModel.addToFavorites(article)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.favRV)
        }
    }

    private fun setUpFavoriteRecycler(){
        newsAdapter = NewsAdapter()
        binding.favRV.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
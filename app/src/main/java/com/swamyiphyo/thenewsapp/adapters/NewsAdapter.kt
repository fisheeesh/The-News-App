package com.swamyiphyo.thenewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.swamyiphyo.thenewsapp.R
import com.swamyiphyo.thenewsapp.databinding.ItemsNewsBinding
import com.swamyiphyo.thenewsapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    /**
     * Inner classes which they have access to the outer class members
     */
    inner class ArticleViewHolder(val binding: ItemsNewsBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * used to efficiently update the contents of the recycler view without
     * refreshing the whole list and to determine the difference between two lists
     */
    private val differCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    private var onItemClickListener : ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(ItemsNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]

        Glide.with(holder.itemView).load(currentArticle.urlToImage).into(holder.binding.articleImage)
        holder.binding.articleTitle.text = currentArticle.title
        holder.binding.articleSource.text = currentArticle.source.name
        holder.binding.articleDateTime.text = currentArticle.publishedAt
        holder.binding.articleDescription.text = currentArticle.description.toString()

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.translate_anim)
        holder.itemView.apply {
            setAnimation(animation)
            setOnClickListener(){
                onItemClickListener?.let {
                    it(currentArticle)
                }
            }
        }
    }

    fun setOnItemClickListener(listener : (Article) -> Unit) {
        onItemClickListener = listener

    }
}
package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.NewsListItemBinding
import com.example.news.model.Article

class NewsAdapter(var articles: List<Article?>?) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val viewBinding =
            NewsListItemBinding
                .inflate(
                    LayoutInflater.from(parent.context), parent,false)
        return NewsViewHolder(viewBinding, onItemClickListener)
    }

    override fun getItemCount() = articles?.size?:0

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemViewBinding.article = articles?.get(position)
    }

    class NewsViewHolder(
        val itemViewBinding: NewsListItemBinding,
        onItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {
        init {
                itemViewBinding.root.setOnClickListener {
                    if (onItemClickListener != null && adapterPosition != RecyclerView.NO_POSITION)
                        onItemClickListener.onItemClick(adapterPosition)
            }
        }
    }

    fun swapList(articles: List<Article?>?) {
        this.articles = articles
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick (position: Int)
    }

}
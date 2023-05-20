package com.example.mvvmnewsapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.example.mvvmnewsapp.ui.model.Article

class NewsAdapter : ListAdapter<Article,NewsAdapter.ArticleViewHolder>(DiffCallBack)
{

    companion object DiffCallBack: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    class ArticleViewHolder(private val binding: ItemArticlePreviewBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide.with(itemView).load(article.urlToImage).into(binding.ivArticleImage)
            binding.tvSource.text = article.source.name
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt
            itemView.setOnClickListener {
                Log.d("OnClick on Item works:", " ${article.source.name}: ${article.url}")
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        return ArticleViewHolder(ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}
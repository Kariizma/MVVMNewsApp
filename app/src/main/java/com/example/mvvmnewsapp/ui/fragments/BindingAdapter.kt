package com.example.mvvmnewsapp.ui.fragments

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnewsapp.ui.adapter.NewsAdapter
import com.example.mvvmnewsapp.ui.model.Article

@BindingAdapter("articleData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>)
{
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}
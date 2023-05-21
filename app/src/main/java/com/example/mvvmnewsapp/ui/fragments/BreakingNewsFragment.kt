package com.example.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.example.mvvmnewsapp.ui.adapter.BreakingNewsAdapter
import com.example.mvvmnewsapp.ui.util.Resource
import com.example.mvvmnewsapp.ui.viewmodel.NewsViewModel

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var breakingNewsAdapter: BreakingNewsAdapter
    private lateinit var binding: FragmentBreakingNewsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentBreakingNewsBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        binding.lifecycleOwner = this
        breakingNewsAdapter = BreakingNewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = breakingNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
               is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsResponse ->
                        breakingNewsAdapter.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {message ->
                        Log.e("Breaking News Fragment:", "an error occured: ${message}")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}
package com.example.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.example.mvvmnewsapp.ui.adapter.SearchNewsAdapter
import com.example.mvvmnewsapp.ui.util.Constants.Companion.SEARCH_NEWS_DELAY
import com.example.mvvmnewsapp.ui.util.Resource
import com.example.mvvmnewsapp.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment: Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: SearchNewsAdapter
    private lateinit var binding: FragmentSearchNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        binding.lifecycleOwner = this
        newsAdapter = SearchNewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        //setting up search functionality
        var Job: Job? = null
        binding.etSearch.addTextChangedListener {editable ->
            Job?.cancel() //when we type something we cancel our current job and start a new one
            Job = MainScope().launch {
                delay(SEARCH_NEWS_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                            newsResponse ->
                        newsAdapter.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {message ->
                        Log.e("Search News Fragment:", "an error occured: ${message}")
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
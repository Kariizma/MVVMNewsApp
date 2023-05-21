package com.example.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.databinding.FragmentArticleBinding
import com.example.mvvmnewsapp.ui.viewmodel.NewsViewModel

class ArticleFragment: Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentArticleBinding
    private val arg: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentArticleBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(arg.article.url)
        }
    }
}
package com.example.news.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.databinding.ArticleBinding
import com.example.news.model.Article
import com.example.news.util.EXTRA_NEWS

class ArticleView : BaseActivity<ViewModel, ArticleBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {

        val article = intent.getParcelableExtra<Article>(EXTRA_NEWS)
        viewDataBinding.article = article

        viewDataBinding.textViewVisitWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article?.url))
            intent.resolveActivity(packageManager)?.apply {
                startActivity(intent)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.article
    override fun getViewModelClass() =
        ViewModelProvider(this).get(ArticleViewModel::class.java)
}
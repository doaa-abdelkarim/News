package com.example.news.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.HomeBinding
import com.example.news.model.Source
import com.example.news.util.EXTRA_NEWS
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeView : BaseActivity<HomeViewModel, HomeBinding>() {

    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNewsAdapter()
        initViews()

        subscribeToLiveData()
        viewModel.getSources()
    }

    override fun getLayoutId(): Int = R.layout.home
    override fun getViewModelClass(): HomeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter(null)
        newsAdapter.onItemClickListener = object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(
                    Intent(this@HomeView, ArticleView::class.java)
                        .putExtra(
                            EXTRA_NEWS, newsAdapter.articles?.get(position)
                        )
                )
            }
        }
    }

    private fun initViews() {
        val snapHelper = PagerSnapHelper()
        viewDataBinding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@HomeView)
            setHasFixedSize(true)
        }
        snapHelper.attachToRecyclerView(viewDataBinding.recyclerViewNews)
    }

    private fun subscribeToLiveData() {
        viewModel.sourcesLiveData.observe(this, Observer {
            initTabLayout(it)
        })

        viewModel.articlesLiveData.observe(this, Observer {
            newsAdapter.swapList(it)
        })

        viewModel.messageLiveData.observe(this, Observer {
            viewDataBinding.textViewError.text = it
        })

        viewModel.isResponseSuccessful.observe(this, Observer {
            if (it)
                showHideViews(View.VISIBLE, View.GONE)
            else
                showHideViews(View.GONE, View.VISIBLE)
        })
    }

    private fun initTabLayout(sources: List<Source?>?) {
        viewDataBinding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewModel.getArticles((tab?.tag as Source).id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.getArticles((tab?.tag as Source).id)
            }

        })
        sources?.forEach {
            val tab = viewDataBinding.tabLayout.newTab()
            tab.text = it?.name
            tab.tag = it
            viewDataBinding.tabLayout.addTab(tab)
        }
    }

    private fun showHideViews(recyclerVisibility: Int, textViewVisibility: Int) {
        viewDataBinding.recyclerViewNews.visibility = recyclerVisibility
        viewDataBinding.textViewError.visibility = textViewVisibility
    }
}
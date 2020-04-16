package com.avisper.mvvm.sampleapp.newsapi.ui.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avisper.mvvm.sampleapp.newsapi.R
import com.avisper.mvvm.sampleapp.newsapi.enums.eCategory
import com.avisper.mvvm.sampleapp.newsapi.network.response.ArticleModel
import com.avisper.mvvm.sampleapp.newsapi.ui.base.IBaseView
import com.avisper.mvvm.sampleapp.newsapi.ui.web.WebActivity
import com.avisper.mvvm.sampleapp.newsapi.utils.IOnUiStateChanged
import com.avisper.mvvm.sampleapp.newsapi.utils.UiState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsActivity : AppCompatActivity(), IBaseView, IOnUiStateChanged {

    private val newsViewModel: NewsViewModelImpl by viewModel()

    private lateinit var articleAdapter: ArticleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initObservers()
    }


    override fun initView() {
        btnBusiness.setOnClickListener { newsViewModel.loadHeadlines(eCategory.business) }

        btnTech.setOnClickListener { newsViewModel.loadHeadlines(eCategory.technology) }

        articleAdapter = ArticleAdapter(object : ArticleItemClickListener {
            override fun onItemClick(item: ArticleModel) {
                openArticle(item)
            }
        })

        rvArticles.apply {
            this.layoutManager =
                LinearLayoutManager(context)
                    .apply {
                        orientation = RecyclerView.VERTICAL
                    }
            this.adapter = this@NewsActivity.articleAdapter
        }
    }


    override fun initObservers() {
        newsViewModel.apply {
            articles.observe(
                this@NewsActivity,
                Observer { articles ->
                    articleAdapter.submitList(articles)
                })

            uiState.observe(
                this@NewsActivity,
                Observer(::onUiStateChanged)
            )
        }
    }

    private fun openArticle(articleModel: ArticleModel) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WebActivity.KEY_URL, articleModel.url)
        startActivity(intent)
    }

    override fun onUiStateChanged(uiState: UiState) {
        loader.visibility =
            if (uiState.showLoader) {
                View.VISIBLE
            } else {
                View.GONE
            }

        if (uiState.shouldDisplayError and !uiState.errorText.isNullOrBlank()) {
            Toast.makeText(this, uiState.errorText, Toast.LENGTH_LONG).show()
        }
    }
}

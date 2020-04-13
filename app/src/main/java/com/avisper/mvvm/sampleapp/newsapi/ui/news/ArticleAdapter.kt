package com.avisper.mvvm.sampleapp.newsapi.ui.news

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avisper.mvvm.sampleapp.newsapi.R
import com.avisper.mvvm.sampleapp.newsapi.network.response.ArticleModel
import com.avisper.mvvm.sampleapp.newsapi.utils.OnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*


class ArticleAdapter(private val articleItemClickListener: ArticleItemClickListener) :
    ListAdapter<ArticleModel, ArticleViewHolder>(ArticleDataComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position), listener = articleItemClickListener)
    }
}

class ArticleViewHolder(
    private val rootView: View
) : RecyclerView.ViewHolder(rootView) {

    fun bind(articleModel: ArticleModel, listener: ArticleItemClickListener) {
        rootView.tvTitle_item_article.text = articleModel.title

        Picasso.get()
            .load(Uri.parse(articleModel.urlToImage ?: ""))
            .placeholder(R.mipmap.ic_launcher)
            .error(R.drawable.ic_launcher_background)
            .into(rootView.ivImg_item_article)

        setClickListener(articleModel, listener)
    }

    private fun setClickListener(
        articleModel: ArticleModel,
        listener: ArticleItemClickListener?
    ) {
        itemView.setOnClickListener {
            listener?.onItemClick(articleModel)
        }
    }
}

class ArticleDataComparator : DiffUtil.ItemCallback<ArticleModel>() {
    override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel) =
        oldItem == newItem
}

interface ArticleItemClickListener : OnItemClickListener<ArticleModel>
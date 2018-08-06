package com.toxicbakery.androidthings.mirror.module.news.feed.ui.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.news.model.Item
import com.toxicbakery.androidthings.mirror.util.bind
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import timber.log.Timber

class NewsFeedRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageImageView: ImageView by bind(R.id.news_feed_article_image)
    private val titleTextView: TextView by bind(R.id.news_feed_article_title)

    init {
        imageImageView.colorFilter = ColorMatrix()
                .apply { setSaturation(0.2f) }
                .let(::ColorMatrixColorFilter)
    }

    fun bind(item: Item) {
        Observable.just(item.description)
                .map(this::extractImageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { loadImage(it) },
                        {
                            Timber.e(it, "Failed to extract image.")
                            loadImage("")
                        })

        titleTextView.text = item.title
    }

    private fun loadImage(url: String) =
            when {
                url.isEmpty() -> imageImageView.setImageDrawable(null)
                else -> Picasso.get()
                        .load(url)
                        .resize(400, 400)
                        .onlyScaleDown()
                        .centerCrop()
                        .into(imageImageView)
            }

    private fun extractImageUrl(input: String): String =
            Jsoup.parse(input)
                    .selectFirst("img")
                    ?.attr("src")
                    ?: ""

}
package com.example.main.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import com.example.core.data.model.movie.Result
import com.example.core.delegate.viewProvider
import com.example.main.R
import com.example.main.ViewBinder

class ImageMovieView : LinearLayoutCompat, ViewBinder<Result> {

    private val imageMovie: AppCompatImageView by viewProvider(R.id.view_image_movie_image)
    private val titleMovie: AppCompatTextView by viewProvider(R.id.view_image_movie_title)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.view_image_movie, this)
        layoutParams = LinearLayoutCompat.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    override fun bind(model: Result) {
        with(model) {
            titleMovie.text = title
        }
    }
}
package com.example.main.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import com.example.core.data.model.movie.Result
import com.example.core.util.GlideApp
import com.example.main.R

import java.util.ArrayList

class MainAdapter (
    private val context: Context,
    private val clickHandler: MainAdapterOnItemClickHandler,
    private val results: List<Result>
) : RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>(), Filterable {
    var items: List<Result> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        val WIDTH_IMAGE = "w342"

        val urlImage = "http://image.tmdb.org/t/p/"
        GlideApp
            .with(context)
            .load(urlImage + WIDTH_IMAGE + items[position].poster_path)
            .placeholder(R.drawable.ic_placeholder_black)
            .into(holder.imageMovie)

        holder.textViewTitleMovie.text = items[position].title
    }

    override fun getItemCount() = items.size

    fun addItems(results: List<Result>) {
        this.items = results
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                if (constraint != null && constraint.isNotEmpty()) {
                    val resultsFilter = ArrayList<Result>()
                    for (result in results) {
                        if (result.title.toLowerCase().contains(constraint)) {
                            resultsFilter.add(result)
                        }
                    }

                    filterResults.count = resultsFilter.size
                    filterResults.values = resultsFilter
                } else {
                    filterResults.count = results.size
                    filterResults.values = results
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                items = results.values as ArrayList<Result>
                notifyDataSetChanged()
            }
        }
    }

    interface MainAdapterOnItemClickHandler {
        fun onItemClick(result: Result)
    }

    //TODO: Extrair essa inner class para uma classe
    inner class MainAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val imageMovie: AppCompatImageView
        val textViewTitleMovie: AppCompatTextView

        init {
            this.imageMovie = view.findViewById(R.id.item_main_image)
            this.textViewTitleMovie = view.findViewById(R.id.item_main_title)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            clickHandler.onItemClick(items[adapterPosition])
        }
    }
}

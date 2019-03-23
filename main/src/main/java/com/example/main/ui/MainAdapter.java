package com.example.main.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.core.data.model.movie.Result;
import com.example.core.util.GlideApp;
import com.example.main.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends
        RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> implements Filterable {

    private Context context;
    private final MainAdapterOnItemClickHandler clickHandler;
    private List<Result> results;
    private List<Result> listResultsFilter;

    MainAdapter(@NonNull Context context, MainAdapterOnItemClickHandler clickHandler, List<Result> results) {
        this.context = context;
        this.clickHandler = clickHandler;
        this.results = results;
        this.listResultsFilter = results;
    }

    @NonNull
    @Override
    public MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new MainAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapterViewHolder holder, int position) {
        String WIDTH_IMAGE = "w342";

        String urlImage = "http://image.tmdb.org/t/p/";
        GlideApp
                .with(context)
                .load(urlImage.concat(WIDTH_IMAGE).concat(listResultsFilter.get(position).getPoster_path()))
                .placeholder(R.drawable.ic_placeholder_black)
                .into(holder.imageMovie);

        holder.textViewTitleMovie.setText(listResultsFilter.get(position).getTitle());
    }

    @Override
    public int getItemCount() { return listResultsFilter == null ? 0 : listResultsFilter.size(); }

    void addItems(List<Result> results) {
        this.listResultsFilter = results;
        notifyDataSetChanged();
    }

    List<Result> getItems() {
        return this.listResultsFilter;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new Filter.FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Result> resultsFilter = new ArrayList<>();
                    for (Result result: results) {
                        if (result.getTitle().toLowerCase().contains(constraint)) {
                            resultsFilter.add(result);
                        }
                    }

                    filterResults.count = resultsFilter.size();
                    filterResults.values = resultsFilter;
                } else {
                    filterResults.count = results.size();
                    filterResults.values = results;
                }

                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listResultsFilter = (ArrayList<Result>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface MainAdapterOnItemClickHandler {
        void onItemClick(Result result);
    }

    //TODO: Extrair essa inner class para uma classe
    class MainAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatImageView imageMovie;
        private AppCompatTextView textViewTitleMovie;

        MainAdapterViewHolder(View view) {
            super(view);
            this.imageMovie = view.findViewById(R.id.item_main_image);
            this.textViewTitleMovie = view.findViewById(R.id.item_main_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onItemClick(listResultsFilter.get(getAdapterPosition()));
        }
    }
}

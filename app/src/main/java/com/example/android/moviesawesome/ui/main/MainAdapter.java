package com.example.android.moviesawesome.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.movie.Result;
import com.example.android.moviesawesome.util.GlideApp;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {

    private Context context;
    private final MainAdapterOnItemClickHandler clickHandler;
    private List<Result> results;
    public static final String URL_IMAGE = "http://image.tmdb.org/t/p/";

    MainAdapter(@NonNull Context context, MainAdapterOnItemClickHandler clickHandler, List<Result> results) {
        this.context = context;
        this.clickHandler = clickHandler;
        this.results = results;
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

        GlideApp
                .with(context)
                .load(URL_IMAGE.concat(WIDTH_IMAGE).concat(results.get(position).getPoster_path()))
                .placeholder(R.drawable.ic_placeholder_black)
                .into(holder.imageMovie);
    }

    @Override
    public int getItemCount() { return results == null ? 0 : results.size(); }

    public void addItems(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public interface MainAdapterOnItemClickHandler {
        void onItemClick(Result result);
    }

    //TODO: Extrair essa inner class para uma classe
    class MainAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageMovie;

        MainAdapterViewHolder(View view) {
            super(view);
            this.imageMovie = view.findViewById(R.id.item_main_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onItemClick(results.get(getAdapterPosition()));
        }
    }
}

package com.example.android.moviesawesome.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.Result;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {

    private Context context;
    private final MainAdapterOnItemClickHandler clickHandler;
    private List<Result> results;

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
        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/".concat("w185").concat(results.get(position).getPoster_path()))
                .into(holder.imageMovie);
    }

    @Override
    public int getItemCount() {
        if (null == results) return 0;
        return results.size();
    }

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

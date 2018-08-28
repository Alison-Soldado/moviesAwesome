package com.example.android.moviesawesome.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.video.ResultVideo;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailAdapterViewHolder> {

    private Context context;
    private final DetailAdapter.DetailAdapterOnItemClickHandler clickHandler;
    private List<ResultVideo> results;

    DetailAdapter(@NonNull Context context, DetailAdapter.DetailAdapterOnItemClickHandler clickHandler, List<ResultVideo> results) {
        this.context = context;
        this.clickHandler = clickHandler;
        this.results = results;
    }

    @NonNull
    @Override
    public DetailAdapter.DetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer, parent, false);
        return new DetailAdapter.DetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailAdapterViewHolder holder, int position) {
        holder.textViewName.setText(results.get(position).getName());
        holder.textViewType.setText(results.get(position).getType());
    }

    @Override
    public int getItemCount() {
        if (null == results) return 0;
        return results.size();
    }

    public void addItems(List<ResultVideo> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public interface DetailAdapterOnItemClickHandler {
        void onItemClick(ResultVideo result);
    }

    //TODO: Extrair essa inner class para uma classe
    class DetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private TextView textViewType;

        DetailAdapterViewHolder(View view) {
            super(view);
            this.textViewName = view.findViewById(R.id.item_trailer_text_name);
            this.textViewType = view.findViewById(R.id.item_trailer_text_type);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onItemClick(results.get(getAdapterPosition()));
        }
    }
}

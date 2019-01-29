package com.example.detail.ui.review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.data.model.review.ResultReview;
import com.example.detail.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private Context context;
    private final ReviewAdapterOnItemClickHandler clickHandler;
    private List<ResultReview> results;

    ReviewAdapter(@NonNull Context context, ReviewAdapterOnItemClickHandler clickHandler, List<ResultReview> results) {
        this.context = context;
        this.clickHandler = clickHandler;
        this.results = results;
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        holder.textViewAuthor.setText(results.get(position).getAuthor());
        holder.textViewContent.setText(results.get(position).getContent());
    }

    @Override
    public int getItemCount() { return results == null ? 0 : results.size(); }

    public void addItems(List<ResultReview> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public interface ReviewAdapterOnItemClickHandler {
        void onItemClick(ResultReview result);
    }

    //TODO: Extrair essa inner class para uma classe
    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewAuthor;
        private TextView textViewContent;

        ReviewAdapterViewHolder(View view) {
            super(view);
            this.textViewAuthor = view.findViewById(R.id.item_review_text_author);
            this.textViewContent = view.findViewById(R.id.item_review_text_content);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onItemClick(results.get(getAdapterPosition()));
        }
    }
}

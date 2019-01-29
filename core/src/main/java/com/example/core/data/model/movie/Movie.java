package com.example.core.data.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private double total_results;
    @SerializedName("total_pages")
    private double total_pages;
    @SerializedName("results")
    private List<Result> results;

    private Movie(Parcel in) {
        page = in.readInt();
        total_results = in.readDouble();
        total_pages = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeDouble(total_results);
        dest.writeDouble(total_pages);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public double getTotal_results() {
        return total_results;
    }

    public void setTotal_results(double total_results) {
        this.total_results = total_results;
    }

    public double getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(double total_pages) {
        this.total_pages = total_pages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}

package com.example.core.data.model.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video implements Parcelable {

    @SerializedName("id")
    private double id;
    @SerializedName("results")
    private List<ResultVideo> results;

    protected Video(Parcel in) {
        id = in.readDouble();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(id);
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public List<ResultVideo> getResults() {
        return results;
    }

    public void setResults(List<ResultVideo> results) {
        this.results = results;
    }

    public static Creator<Video> getCREATOR() {
        return CREATOR;
    }
}

package com.example.core.data.model.review;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResultReview implements Parcelable {

    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    protected ResultReview(Parcel in) {
        author = in.readString();
        content = in.readString();
        id = in.readString();
        url = in.readString();
    }

    public static final Creator<ResultReview> CREATOR = new Creator<ResultReview>() {
        @Override
        public ResultReview createFromParcel(Parcel in) {
            return new ResultReview(in);
        }

        @Override
        public ResultReview[] newArray(int size) {
            return new ResultReview[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(id);
        dest.writeString(url);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Creator<ResultReview> getCREATOR() {
        return CREATOR;
    }
}

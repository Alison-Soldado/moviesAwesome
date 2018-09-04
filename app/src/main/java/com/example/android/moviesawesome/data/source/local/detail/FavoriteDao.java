package com.example.android.moviesawesome.data.source.local.detail;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.moviesawesome.data.model.movie.Result;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM result")
    LiveData<List<Result>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertFavorite(Result result);

    @Delete
    void deleteFavorite(Result result);

    @Query("SELECT * FROM result WHERE id = :id")
    LiveData<Result> getFavoriteById(long id);
}

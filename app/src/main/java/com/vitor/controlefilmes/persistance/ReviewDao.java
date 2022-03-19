package com.vitor.controlefilmes.persistance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vitor.controlefilmes.entity.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    long insert(Review review);

    @Delete
    void delete(Review review);

    @Update
    void update(Review review);

    @Query("SELECT * FROM review WHERE id = :id")
    Review queryForId(long id);

    @Query("SELECT * FROM review")
    List<Review> queryAll();
}

package com.vitor.controlefilmes.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vitor.controlefilmes.entity.Review;

@Database(entities = {Review.class}, version = 1, exportSchema = false)
public abstract class ReviewsDatabase extends RoomDatabase {

    public abstract ReviewDao reviewDao();

    private static ReviewsDatabase instance;

    public static ReviewsDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (ReviewsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, ReviewsDatabase.class, "movie_reviews.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}

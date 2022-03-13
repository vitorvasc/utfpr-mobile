package com.vitor.controlefilmes.persistance;

import static com.vitor.controlefilmes.service.Constants.*;
import static com.vitor.controlefilmes.service.Constants.REVIEWS_COLUMN_HAS_SPOILERS;
import static com.vitor.controlefilmes.service.Constants.REVIEWS_COLUMN_RECOMMENDS;
import static com.vitor.controlefilmes.service.Constants.REVIEWS_COLUMN_REVIEW;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vitor.controlefilmes.entity.Review;

import java.util.ArrayList;
import java.util.List;

public final class ReviewDAO {

    private ReviewsDatabase connection;
    public List<Review> reviewList;

    public ReviewDAO(ReviewsDatabase reviewsDatabase) {
        connection = reviewsDatabase;
        reviewList = new ArrayList<>();
    }

    public void createTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + REVIEWS_TABLE + "(" +
                REVIEWS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                REVIEWS_COLUMN_TITLE + " TEXT NOT NULL, " +
                REVIEWS_COLUMN_GENRE + " TEXT NOT NULL, " +
                REVIEWS_COLUMN_RATING + " REAL, " +
                REVIEWS_COLUMN_REVIEW + " TEXT NOT NULL, " +
                REVIEWS_COLUMN_HAS_SPOILERS + " INTEGER, " +
                REVIEWS_COLUMN_RECOMMENDS + " INTEGER)";

        db.execSQL(sql);
    }

    public void deleteTable(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + REVIEWS_TABLE;
        db.execSQL(sql);
    }

    public boolean insert(final Review review) {
        ContentValues values = new ContentValues();

        values.put(REVIEWS_COLUMN_TITLE, review.getTitle());
        values.put(REVIEWS_COLUMN_GENRE, review.getGenre());
        values.put(REVIEWS_COLUMN_RATING, review.getRating());
        values.put(REVIEWS_COLUMN_REVIEW, review.getReview());
        values.put(REVIEWS_COLUMN_HAS_SPOILERS, review.hasSpoilers());
        values.put(REVIEWS_COLUMN_RECOMMENDS, review.getRecommends());

        long id = connection.getWritableDatabase().insert(REVIEWS_TABLE, null, values);

        Review.Builder reviewBuilder = Review.newBuilder();
        reviewBuilder.withId(id)
                .withTitle(review.getTitle())
                .withGenre(review.getGenre())
                .withRating(review.getRating())
                .withReview(review.getReview())
                .hasSpoilers(review.hasSpoilers())
                .withRecommends(review.getRecommends());

        reviewList.add(reviewBuilder.build());

        return true;
    }

    public boolean alter(final Review review) {
        ContentValues values = new ContentValues();

        values.put(REVIEWS_COLUMN_TITLE, review.getTitle());
        values.put(REVIEWS_COLUMN_GENRE, review.getGenre());
        values.put(REVIEWS_COLUMN_RATING, review.getRating());
        values.put(REVIEWS_COLUMN_REVIEW, review.getReview());
        values.put(REVIEWS_COLUMN_HAS_SPOILERS, review.hasSpoilers());
        values.put(REVIEWS_COLUMN_RECOMMENDS, review.getRecommends());

        String[] args = {String.valueOf(review.getId())};

        connection.getWritableDatabase().update(REVIEWS_TABLE, values, REVIEWS_COLUMN_ID + " = ?", args);

        int listIndex = -1;
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getId() == review.getId()) {
                listIndex = i;
            }
        }

        if (listIndex != -1) {
            reviewList.remove(listIndex);
            reviewList.add(listIndex, review);
        }

        return true;
    }

    public boolean delete(final Review review) {
        String[] args = {String.valueOf(review.getId())};

        connection.getWritableDatabase().delete(REVIEWS_TABLE, REVIEWS_COLUMN_ID + " = ?", args);
        reviewList.remove(review);

        return true;
    }

    public void load() {
        reviewList.clear();

        String sql = "SELECT * FROM " + REVIEWS_TABLE;

        Cursor cursor = connection.getReadableDatabase().rawQuery(sql, null);

        int columnId = cursor.getColumnIndex(REVIEWS_COLUMN_ID);
        int columnTitle = cursor.getColumnIndex(REVIEWS_COLUMN_TITLE);
        int columnGenre = cursor.getColumnIndex(REVIEWS_COLUMN_GENRE);
        int columnRating = cursor.getColumnIndex(REVIEWS_COLUMN_RATING);
        int columnReview = cursor.getColumnIndex(REVIEWS_COLUMN_REVIEW);
        int columnHasSpoilers = cursor.getColumnIndex(REVIEWS_COLUMN_HAS_SPOILERS);
        int columnRecommends = cursor.getColumnIndex(REVIEWS_COLUMN_RECOMMENDS);

        while (cursor.moveToNext()) {
            Review.Builder reviewBuilder = Review.newBuilder();

            boolean hasSpoilers = cursor.getInt(columnHasSpoilers) == 0
                    ? Boolean.FALSE
                    : Boolean.TRUE;

            reviewBuilder.withId(cursor.getLong(columnId))
                    .withTitle(cursor.getString(columnTitle))
                    .withGenre(cursor.getString(columnGenre))
                    .withRating(cursor.getFloat(columnRating))
                    .withReview(cursor.getString(columnReview))
                    .hasSpoilers(hasSpoilers)
                    .withRecommends(cursor.getInt(columnRecommends));

            reviewList.add(reviewBuilder.build());
        }

        cursor.close();
    }

    public Review getReviewById(long id) {
        for (Review r : reviewList) {
            if (r.getId() == id) {
                return r;
            }
        }

        return null;
    }
}

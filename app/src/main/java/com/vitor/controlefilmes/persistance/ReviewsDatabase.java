package com.vitor.controlefilmes.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class ReviewsDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "reviews.db";
    private static final int DB_VERSION = 1;

    private static ReviewsDatabase instance;

    private Context context;
    public ReviewDAO reviewDAO;

    public static ReviewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new ReviewsDatabase(context);
        }

        return instance;
    }

    private ReviewsDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;

        reviewDAO = new ReviewDAO(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        reviewDAO.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int i1) {
        reviewDAO.deleteTable(db);
        onCreate(db);
    }
}

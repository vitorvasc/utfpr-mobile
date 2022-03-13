package com.vitor.controlefilmes.service;

import com.vitor.controlefilmes.R;

public final class Constants {

    public static final String WEBSITE_UTFPR = "https://www.utfpr.edu.br";

    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String REVIEW = "REVIEW";

    public static final int REQUEST_CODE_ADD_REVIEW = 1;
    public static final int REQUEST_CODE_EDIT_REVIEW = 2;
    public static final int REQUEST_CODE_RELOAD_PREFERENCES = 3;

    public static final String SHARED_PREFERENCES_FILE_NAME = "com.vitor.controlefilmes.sharedpreferences.ORDER_PREFERENCES";
    public static final String KEY_SETTINGS_ORDER_BY = "SETTINGS_ORDER_BY";

    public static final int ORDER_BY_RATING = R.id.radioButtonOrderRating;
    public static final int ORDER_BY_MOVIE_NAME = R.id.radioButtonOrderMovieName;

    public static final String REVIEWS_TABLE = "REVIEWS";
    public static final String REVIEWS_COLUMN_ID = "ID";
    public static final String REVIEWS_COLUMN_TITLE = "TITLE";
    public static final String REVIEWS_COLUMN_GENRE = "GENRE";
    public static final String REVIEWS_COLUMN_RATING = "RATING";
    public static final String REVIEWS_COLUMN_REVIEW = "REVIEW";
    public static final String REVIEWS_COLUMN_HAS_SPOILERS = "HAS_SPOILERS";
    public static final String REVIEWS_COLUMN_RECOMMENDS = "RECOMMENDS";
}

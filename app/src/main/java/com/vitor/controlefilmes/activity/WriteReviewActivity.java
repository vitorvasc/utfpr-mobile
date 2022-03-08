package com.vitor.controlefilmes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.adapter.MovieListAdapter;
import com.vitor.controlefilmes.dto.Review;
import com.vitor.controlefilmes.entity.Category;
import com.vitor.controlefilmes.entity.Movie;
import com.vitor.controlefilmes.service.Constants;

import java.util.ArrayList;

public final class WriteReviewActivity extends AppCompatActivity {

    private EditText editTextWriteReview;
    private Spinner spinnerMovieTitle;
    private RatingBar ratingBarRating;
    private CheckBox checkBoxContainSpoilers;
    private RadioGroup radioGroupRecommends;
    private RadioButton radioButtonRecommendsYes, radioButtonRecommendsNo;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.submit_review);
        setContentView(R.layout.activity_write_review);

        spinnerMovieTitle = findViewById(R.id.spinnerMovieTitle);
        editTextWriteReview = findViewById(R.id.editTextWriteReview);
        ratingBarRating = findViewById(R.id.ratingBarRating);
        checkBoxContainSpoilers = findViewById(R.id.checkBoxContainSpoilers);
        radioGroupRecommends = findViewById(R.id.radioGroupRecommends);
        radioButtonRecommendsYes = findViewById(R.id.radioButtonRecommendsYes);
        radioButtonRecommendsNo = findViewById(R.id.radioButtonRecommendsNo);

        populateMoviesSpinner();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            requestCode = bundle.getInt(Constants.REQUEST_CODE, 0);

            if (requestCode == Constants.REQUEST_CODE_ADD_REVIEW) {
                setTitle(getString(R.string.write_review));
            } else if (requestCode == Constants.REQUEST_CODE_EDIT_REVIEW) {
                setTitle(getString(R.string.edit_review));
                Review review = (Review) bundle.getSerializable(Constants.REVIEW);
                spinnerMovieTitle.setSelection(getMovieIndex(review.getTitle()));
                editTextWriteReview.setText(review.getReview());
                ratingBarRating.setRating(review.getRating());
                checkBoxContainSpoilers.setChecked(review.hasSpoilers());
                if (review.getRecommends() == 0) {
                    radioButtonRecommendsNo.setChecked(true);
                } else {
                    radioButtonRecommendsYes.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        super.onBackPressed();
    }

    public static void addReview(AppCompatActivity activity) {
        Intent intent = new Intent(activity, WriteReviewActivity.class);
        intent.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ADD_REVIEW);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_ADD_REVIEW);
    }

    public static void editReview(AppCompatActivity activity, Review review) {
        Intent intent = new Intent(activity, WriteReviewActivity.class);
        intent.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_EDIT_REVIEW);
        intent.putExtra(Constants.REVIEW, review);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_REVIEW);
    }

    public void clearForm(View view) {
        editTextWriteReview.setText(null);

        ratingBarRating.setRating(0F);
        checkBoxContainSpoilers.setChecked(false);
        radioGroupRecommends.clearCheck();

        Toast.makeText(this, R.string.form_cleared_successfully, Toast.LENGTH_LONG).show();
    }

    public void submitForm(View view) {

        final Review.Builder reviewBuilder = Review.newBuilder();

        Movie selectedMovie = (Movie) spinnerMovieTitle.getSelectedItem();

        Review review = reviewBuilder.withTitle(selectedMovie.getName())
                .withGenre(selectedMovie.getCategory().getName())
                .withRating(ratingBarRating.getRating())
                .withReview(editTextWriteReview.getText().toString())
                .hasSpoilers(checkBoxContainSpoilers.isChecked())
                .withRecommends(radioGroupRecommends.getCheckedRadioButtonId() == R.id.radioButtonRecommendsYes
                        ? 1
                        : 0)
                .build();

        if (!validateForm(review)) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.REVIEW, review);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private boolean validateForm(Review review) {
        if (review.getRating() == 0F) {
            showValidationErrorMessage(getString(R.string.rating));
            ratingBarRating.requestFocus();
            return false;
        }

        if (review.getReview() == null || review.getReview().trim().isEmpty()) {
            showValidationErrorMessage(getString(R.string.write_review));
            editTextWriteReview.requestFocus();
            return false;
        }

        if (review.getRecommends() == -1) {
            showValidationErrorMessage(getString(R.string.recommends_movie));
            radioGroupRecommends.requestFocus();
            return false;
        }

        return true;
    }

    private void showValidationErrorMessage(String fieldName) {
        Toast.makeText(this, getString(R.string.field_can_not_be_empty) + "\n- " + fieldName, Toast.LENGTH_LONG).show();
    }

    private void populateMoviesSpinner() {
        final String[] arrayName = getResources().getStringArray(R.array.popular_movies_names);
        final TypedArray arrayImages = getResources().obtainTypedArray(R.array.popular_movies_images);
        final int[] arrayYear = getResources().getIntArray(R.array.popular_movies_year);
        final String[] arrayDescription = getResources().getStringArray(R.array.popular_movies_descriptions);
        final int[] arrayDuration = getResources().getIntArray(R.array.popular_movies_duration);
        final String[] arrayGenres = getResources().getStringArray(R.array.popular_movies_genres);

        final ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < arrayName.length; i++) {
            movies.add(createNewMovie(
                    arrayName[i],
                    arrayImages.getDrawable(i),
                    arrayYear[i],
                    arrayDescription[i],
                    arrayDuration[i],
                    arrayGenres[i]
            ));
        }

        MovieListAdapter adapter = new MovieListAdapter(
                this,
                movies
        );

        spinnerMovieTitle.setAdapter(adapter);
    }

    private Movie createNewMovie(final String name,
                                 final Drawable image,
                                 final int year,
                                 final String description,
                                 final int duration,
                                 final String genreName
    ) {
        final Movie.Builder movieBuilder = Movie.newBuilder();

        return movieBuilder.withName(name)
                .withImage(image)
                .withYear(year)
                .withDescription(description)
                .withDuration(duration)
                .withCategory(createNewCategory(genreName))
                .build();
    }

    private Category createNewCategory(String genreName) {
        final Category.Builder categoryBuilder = Category.newBuilder();

        return categoryBuilder.withName(genreName)
                .build();
    }

    private int getMovieIndex(String movieTitle) {
        for (int i = 0; i < spinnerMovieTitle.getCount(); i++) {
            Movie movie = (Movie) spinnerMovieTitle.getItemAtPosition(i);
            if (movie.getName().equals(movieTitle)) {
                return i;
            }
        }

        return 0;
    }
}
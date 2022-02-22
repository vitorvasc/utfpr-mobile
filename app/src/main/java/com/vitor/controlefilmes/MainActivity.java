package com.vitor.controlefilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.vitor.controlefilmes.dto.Review;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMovieTitle, editTextWriteReview;
    private Spinner spinnerMovieGenre;
    private RatingBar ratingBarRating;
    private CheckBox checkBoxContainSpoilers;
    private RadioGroup radioGroupRecommends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.submit_review);
        setContentView(R.layout.activity_main);

        editTextMovieTitle = findViewById(R.id.editTextMovieTitle);
        editTextWriteReview = findViewById(R.id.editTextWriteReview);
        spinnerMovieGenre = findViewById(R.id.spinnerMovieGenre);
        ratingBarRating = findViewById(R.id.ratingBarRating);
        checkBoxContainSpoilers = findViewById(R.id.checkBoxContainSpoilers);
        radioGroupRecommends = findViewById(R.id.radioGroupRecommends);
    }

    public void clearForm(View view) {
        editTextMovieTitle.setText(null);
        editTextWriteReview.setText(null);

        ratingBarRating.setRating(0F);
        checkBoxContainSpoilers.setChecked(false);
        radioGroupRecommends.clearCheck();

        Toast.makeText(this, R.string.form_cleared_successfully, Toast.LENGTH_LONG).show();
    }

    public void submitForm(View view) {

        final Review.Builder reviewBuilder = Review.newBuilder();

        Review review = reviewBuilder.withTitle(editTextMovieTitle.getText().toString())
                .withGenre((String) spinnerMovieGenre.getSelectedItem())
                .withRating(ratingBarRating.getRating())
                .withReview(editTextWriteReview.getText().toString())
                .hasSpoilers(checkBoxContainSpoilers.isChecked())
                .withRecommends(radioGroupRecommends.getCheckedRadioButtonId())
                .build();

        if (!validateForm(review)) {
            return;
        }

        String successMessage = "";

        successMessage += getString(R.string.movie_title) + ": " + review.getTitle() + "\n";
        successMessage += getString(R.string.movie_genre) + ": " + review.getGenre() + "\n";
        successMessage += getString(R.string.rating) + ": " + review.getRating() + "\n";
        successMessage += getString(R.string.review) + ": " + review.getReview() + "\n";
        successMessage += getString(R.string.has_spoilers) + ": " + (review.hasSpoilers() ? getString(R.string.yes) : getString(R.string.no)) + "\n";
        successMessage += getString(R.string.recommends_movie) + " "
                + (review.getRecommends() == R.id.radioButtonRecommendsYes
                ? getString(R.string.yes)
                : getString(R.string.no)) + "\n";

        Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
    }

    private boolean validateForm(Review review) {
        if (review.getTitle() == null || review.getTitle().trim().isEmpty()) {
            showValidationErrorMessage(getString(R.string.movie_title));
            editTextMovieTitle.requestFocus();
            return false;
        }

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
}
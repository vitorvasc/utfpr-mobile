package com.vitor.controlefilmes.activity;

import static com.vitor.controlefilmes.service.Constants.REQUEST_CODE_ADD_REVIEW;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Pair;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.adapter.ReviewListAdapter;
import com.vitor.controlefilmes.dto.Review;
import com.vitor.controlefilmes.service.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class MoviesListActivity extends AppCompatActivity {

    private ReviewListAdapter reviewsAdapter;
    private ArrayList<Pair<Review, Drawable>> reviewsList;
    private Map<String, Drawable> movieImages;
    private ListView listReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        listReviews = findViewById(R.id.listReviews);

        listReviews.setOnItemClickListener((adapterView, view, position, id) -> {
            Review review = (Review) listReviews.getItemAtPosition(position);
            Toast.makeText(this, review.getTitle(), Toast.LENGTH_LONG).show();
        });

        loadMovieImages();
        populateReviewsList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, R.string.action_canceled, Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constants.REQUEST_CODE_ADD_REVIEW && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Review review = (Review) bundle.getSerializable(Constants.REVIEW);
                reviewsList.add(Pair.create(review, movieImages.get(review.getTitle())));
                reviewsAdapter.notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addReviewFromButton(View view) {
        showReviewActivity();
    }

    public void showAboutActivity(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void showReviewActivity() {
        Intent intent = new Intent(this, WriteReviewActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_REVIEW);
    }

    private void populateReviewsList() {
        reviewsList = new ArrayList<>();
        reviewsAdapter = new ReviewListAdapter(
                this,
                reviewsList
        );
        listReviews.setAdapter(reviewsAdapter);
    }

    private void loadMovieImages() {
        final String[] arrayName = getResources().getStringArray(R.array.popular_movies_names);
        final TypedArray arrayImages = getResources().obtainTypedArray(R.array.popular_movies_images);
        movieImages = new HashMap<>();
        for (int i = 0; i < arrayName.length; i++) {
            movieImages.put(arrayName[i], arrayImages.getDrawable(i));
        }
    }
}
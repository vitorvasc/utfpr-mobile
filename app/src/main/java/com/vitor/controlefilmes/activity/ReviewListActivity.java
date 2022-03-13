package com.vitor.controlefilmes.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Pair;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.adapter.ReviewListAdapter;
import com.vitor.controlefilmes.entity.Review;
import com.vitor.controlefilmes.persistance.ReviewsDatabase;
import com.vitor.controlefilmes.service.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ReviewListActivity extends AppCompatActivity {

    private ReviewListAdapter reviewsAdapter;
    private ArrayList<Pair<Review, Drawable>> reviewsList;
    private Map<String, Drawable> movieImages;
    private ListView listViewReviews;

    private int orderBy = Constants.ORDER_BY_RATING;
    private int selectedPosition = -1;
    private androidx.appcompat.view.ActionMode actionMode;
    private View selectedView;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu_review_list_item_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuItemReviewSelectedEdit:
                    editReview();
                    actionMode.finish();
                    return true;
                case R.id.menuItemReviewSelectedDelete:
                    deleteReview();
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (selectedView != null) {
                selectedView.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            selectedView = null;

            listViewReviews.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        ReviewsDatabase database = ReviewsDatabase.getInstance(this);
        database.reviewDAO.load();

        listViewReviews = findViewById(R.id.listReviews);
        listViewReviews.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewReviews.setOnItemClickListener((adapterView, view, position, id) -> {
            selectedPosition = position;
            editReview();
        });

        listViewReviews.setOnItemLongClickListener((adapterView, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }

            selectedPosition = position;
            view.setBackgroundColor(Color.LTGRAY);
            selectedView = view;
            listViewReviews.setEnabled(false);
            actionMode = startSupportActionMode(mActionModeCallback);
            return true;
        });

        loadMovieImages();
        populateReviewsList();
        loadSharedPreferences();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE_ADD_REVIEW) {
                populateReviewsList();
                orderReviewList();
                reviewsAdapter.notifyDataSetChanged();
            } else if (requestCode == Constants.REQUEST_CODE_EDIT_REVIEW) {
                populateReviewsList();
                orderReviewList();
                reviewsAdapter.notifyDataSetChanged();
            } else if (requestCode == Constants.REQUEST_CODE_RELOAD_PREFERENCES) {
                loadSharedPreferences();
                orderReviewList();
                reviewsAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, R.string.action_canceled, Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_review_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                WriteReviewActivity.addReview(this);
                return true;
            case R.id.menuItemAbout:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItemSettings:
                SettingsActivity.loadSettings(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editReview() {
        WriteReviewActivity.editReview(this, reviewsList.get(selectedPosition).first);
    }

    private void deleteReview() {
        Context context = this;
        DialogInterface.OnClickListener listener = (dialogInterface, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Review selectedReview = reviewsList.get(selectedPosition).first;
                    ReviewsDatabase database = ReviewsDatabase.getInstance(context);
                    database.reviewDAO.delete(selectedReview);
                    reviewsList.remove(selectedPosition);
                    reviewsAdapter.notifyDataSetChanged();
                    orderReviewList();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_action);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(R.string.entry_delete_confirmation);

        builder.setPositiveButton(getString(R.string.yes), listener);
        builder.setNegativeButton(getString(R.string.no), listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void populateReviewsList() {
        ReviewsDatabase database = ReviewsDatabase.getInstance(this);
        reviewsList = new ArrayList<>();

        for (Review r : database.reviewDAO.reviewList) {
            reviewsList.add(Pair.create(r, movieImages.get(r.getTitle())));
        }

        reviewsAdapter = new ReviewListAdapter(
                this,
                reviewsList
        );
        listViewReviews.setAdapter(reviewsAdapter);
    }

    private void loadMovieImages() {
        final String[] arrayName = getResources().getStringArray(R.array.popular_movies_names);
        final TypedArray arrayImages = getResources().obtainTypedArray(R.array.popular_movies_images);
        movieImages = new HashMap<>();
        for (int i = 0; i < arrayName.length; i++) {
            movieImages.put(arrayName[i], arrayImages.getDrawable(i));
        }
    }

    private void loadSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        orderBy = preferences.getInt(Constants.KEY_SETTINGS_ORDER_BY, orderBy);
    }

    private void orderReviewList() {
        switch (orderBy) {
            case Constants.ORDER_BY_RATING:
                Collections.sort(reviewsList, (o1, o2) -> Float.compare(o2.first.getRating(), o1.first.getRating()));
                break;
            case Constants.ORDER_BY_MOVIE_NAME:
                Collections.sort(reviewsList, (o1, o2) -> o1.first.getTitle().compareTo(o2.first.getTitle()));
                break;
        }
    }
}
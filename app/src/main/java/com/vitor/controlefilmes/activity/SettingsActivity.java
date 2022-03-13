package com.vitor.controlefilmes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.service.Constants;

public final class SettingsActivity extends AppCompatActivity {

    private RadioGroup radioGroupOrderBy;
    private RadioButton radioButtonOrderRating, radioButtonOrderMovieName;

    private int orderBy = Constants.ORDER_BY_RATING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.settings));

        radioGroupOrderBy = findViewById(R.id.radioGroupOrderBy);
        radioButtonOrderRating = findViewById(R.id.radioButtonOrderRating);
        radioButtonOrderMovieName = findViewById(R.id.radioButtonOrderMovieName);

        loadSharedPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static void loadSettings(AppCompatActivity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        intent.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_RELOAD_PREFERENCES);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_RELOAD_PREFERENCES);
    }

    public void savePreferences(View view) {
        writeSharedPreferences(radioGroupOrderBy.getCheckedRadioButtonId());
    }

    private void writeSharedPreferences(final int value) {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.KEY_SETTINGS_ORDER_BY, value);
        editor.commit();
        orderBy = value;
        Toast.makeText(this, R.string.value_changed_successfully, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_RELOAD_PREFERENCES);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void loadSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        orderBy = preferences.getInt(Constants.KEY_SETTINGS_ORDER_BY, orderBy);
        setCheckedOrderByPreference();
    }

    private void setCheckedOrderByPreference() {
        switch (orderBy) {
            case Constants.ORDER_BY_RATING:
                radioButtonOrderRating.setChecked(true);
                break;
            case Constants.ORDER_BY_MOVIE_NAME:
                radioButtonOrderMovieName.setChecked(true);
                break;
        }
    }
}
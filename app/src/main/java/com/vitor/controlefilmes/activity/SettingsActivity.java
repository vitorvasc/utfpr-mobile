package com.vitor.controlefilmes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.service.Constants;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup radioGroupOrderBy;
    private RadioButton radioButtonOrderDefault, radioButtonOrderRating, radioButtonOrderMovieName;

    private int orderBy = R.id.radioButtonOrderDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.settings));

        radioGroupOrderBy = findViewById(R.id.radioGroupOrderBy);
        radioButtonOrderDefault = findViewById(R.id.radioButtonOrderDefault);
        radioButtonOrderRating = findViewById(R.id.radioButtonOrderRating);
        radioButtonOrderMovieName = findViewById(R.id.radioButtonOrderMovieName);

        loadSharedPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    }

    private void loadSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        orderBy = preferences.getInt(Constants.KEY_SETTINGS_ORDER_BY, orderBy);
        setCheckedOrderByPreference();
    }

    private void setCheckedOrderByPreference() {
        switch (orderBy) {
            case R.id.radioButtonOrderDefault:
                radioButtonOrderDefault.setChecked(true);
                break;
            case R.id.radioButtonOrderRating:
                radioButtonOrderRating.setChecked(true);
                break;
            case R.id.radioButtonOrderMovieName:
                radioButtonOrderMovieName.setChecked(true);
                break;
        }
    }
}
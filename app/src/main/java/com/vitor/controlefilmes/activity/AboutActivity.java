package com.vitor.controlefilmes.activity;

import static com.vitor.controlefilmes.service.Constants.WEBSITE_UTFPR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.service.HandleSystemIntents;

public final class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(getString(R.string.about));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

    public void handleWebsiteClick(View view) {
        HandleSystemIntents.handleActionViewIntent(this, WEBSITE_UTFPR);
    }

    public void handleEmailClick(View view) {
        final String[] emails = new String[]{getString(R.string.app_owner_email)};
        final String subject = getString(R.string.app_name);
        HandleSystemIntents.handleActionSendTo(this, emails, subject);
    }
}
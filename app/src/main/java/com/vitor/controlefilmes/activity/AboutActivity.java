package com.vitor.controlefilmes.activity;

import static com.vitor.controlefilmes.service.Constants.WEBSITE_UTFPR;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.service.HandleSystemIntents;

public final class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
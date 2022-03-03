package com.vitor.controlefilmes.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitor.controlefilmes.R;

public final class HandleSystemIntents extends AppCompatActivity {

    public static void handleActionViewIntent(final Context context, final String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            actionNotPossible(context);
        }
    }

    public static void handleActionSendTo(final Context context, final String[] addresses, final String subject) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            actionNotPossible(context);
        }
    }

    private static void actionNotPossible(final Context context) {
        Toast.makeText(context, context.getString(R.string.action_not_possible), Toast.LENGTH_SHORT).show();
    }
}

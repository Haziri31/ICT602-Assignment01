package com.example.assignment01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set application layout
        setContentView(R.layout.activity_about);

        //Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);

        // Set up ShareActionProvider
        MenuItem shareItem = menu.findItem(R.id.menu_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if (shareActionProvider != null)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the Zakat Gold Calculator app!");
            shareActionProvider.setShareIntent(shareIntent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_share)
        {
            shareApp();
            return true;
        }

        else if (item.getItemId() == R.id.menu_item_main)
        {
            Intent aboutIntent = new Intent(this, MainActivity.class);
            startActivity(aboutIntent);
            return true;
        }

        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    private void shareApp()
    {
        String appWebsiteLink = "https://github.com/Haziri31/ICT602-Assignment01";

        String shareMessage = "Check out the Zakat Gold Calculator app!\n\n" +
                "Open in gitHub:\n" + appWebsiteLink;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
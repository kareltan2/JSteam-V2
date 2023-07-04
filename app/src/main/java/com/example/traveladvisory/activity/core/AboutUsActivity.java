package com.example.traveladvisory.activity.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveladvisory.R;

/**
 * @author kareltan
 */
public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about_us);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void init() {
        Button submitWish = findViewById(R.id.button_submit_about_us);

        submitWish.setOnClickListener(view -> {
            Toast.makeText(AboutUsActivity.this, "Thanks for supporting TravelAdvisory!", Toast.LENGTH_SHORT).show();
            Intent intentHome = new Intent(AboutUsActivity.this, HomePageActivity.class);
            intentHome.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(intentHome);
        });

        Button viewLocation = findViewById(R.id.view_location);

        viewLocation.setOnClickListener(view ->
                startActivity(new Intent(AboutUsActivity.this, MapsActivity.class))
        );
    }
}
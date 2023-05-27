package com.example.jsteam.activity.core;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsteam.R;
import com.example.jsteam.model.DatabaseConfiguration;

/**
 * @author kareltan
 */
public class GamesDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_games_detail);

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
        TextView gameName = findViewById(R.id.tv_title_game_on_game_detail_page);
        TextView gameGenre = findViewById(R.id.tv_genre_name_game_on_game_detail_page);
        TextView gameRating = findViewById(R.id.tv_rating_value_game_on_game_detail_page);
        TextView gamePrice = findViewById(R.id.tv_price_value_game_on_game_detail_page);
        TextView gameDescription = findViewById(R.id.tv_description_game_on_game_detail_page);
        EditText review = findViewById(R.id.pt_input_review_game_detail);
        Button reviewGameButton = findViewById(R.id.button_add_review_game_detail);

        String gameNameText = getIntent().getStringExtra("gameName");
        String gameGenreText = getIntent().getStringExtra("gameGenre");
        String gameRatingText = getIntent().getStringExtra("gameRating");
        String gamePriceText = getIntent().getStringExtra("gamePrice");
        String gameDescriptionText = getIntent().getStringExtra("gameDescription");

        gameName.setText(gameNameText);
        gameGenre.setText(gameGenreText);
        gameRating.setText(gameRatingText);
        gamePrice.setText(String.format("Rp.%s", gamePriceText));
        gameDescription.setText(gameDescriptionText);

        reviewGameButton.setOnClickListener(view -> {
            if(!String.valueOf(review.getText()).isEmpty()){
                DatabaseConfiguration.DatabaseReview(gameNameText, String.valueOf(review.getText()), getIntent().getStringExtra("username"));
                Toast.makeText(GamesDetailActivity.this, "Review Submitted!", Toast.LENGTH_SHORT).show();
                Intent intentHome = new Intent(GamesDetailActivity.this, HomePageActivity.class);
                intentHome.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intentHome);
            } else {
                Toast.makeText(GamesDetailActivity.this, "The content must not be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
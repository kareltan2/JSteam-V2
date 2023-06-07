package com.example.jsteam.activity.core;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsteam.R;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.ReviewHelper;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.dao.Game;
import com.example.jsteam.model.dao.Review;
import com.example.jsteam.model.dao.User;

/**
 * @author kareltan
 */
public class GamesDetailActivity extends AppCompatActivity {

    private final ReviewHelper reviewHelper = new ReviewHelper(this);

    private final UserHelper userHelper = new UserHelper(this);

    private final GameHelper gameHelper = new GameHelper(this);

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
        ImageView gameImage = findViewById(R.id.iv_game_on_review_section);
        TextView gameDescription = findViewById(R.id.tv_description_game_on_game_detail_page);
        EditText review = findViewById(R.id.pt_input_review_game_detail);
        Button reviewGameButton = findViewById(R.id.button_add_review_game_detail);

        userHelper.open();
        User user = userHelper.findUser(getIntent().getStringExtra("username"));
        userHelper.close();

        gameHelper.open();
        Game game = gameHelper.findGame(getIntent().getIntExtra("gameId", 1));
        gameHelper.close();

        gameName.setText(game.getName());
        gameGenre.setText(game.getGenre());
        gameRating.setText(String.valueOf(game.getRating()));
        gamePrice.setText(game.getPrice());
        gameDescription.setText(game.getDescription());

        reviewGameButton.setOnClickListener(view -> {
            if(!String.valueOf(review.getText()).isEmpty()){
                reviewHelper.open();
                reviewHelper.insertReview(new Review(null, user.getId(), game.getId(), review.getText().toString()));
                reviewHelper.close();
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
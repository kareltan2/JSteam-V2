package com.example.traveladvisory.activity.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveladvisory.R;
import com.example.traveladvisory.helper.PlaceHelper;
import com.example.traveladvisory.helper.UserHelper;
import com.example.traveladvisory.helper.WishHelper;
import com.example.traveladvisory.model.dao.Place;
import com.example.traveladvisory.model.dao.User;
import com.example.traveladvisory.model.dao.Wish;
import com.example.traveladvisory.support.ImageLoaderTask;

/**
 * @author kareltan
 */
public class GamesDetailActivity extends AppCompatActivity {

    private final WishHelper wishHelper = new WishHelper(this);

    private final UserHelper userHelper = new UserHelper(this);

    private final PlaceHelper placeHelper = new PlaceHelper(this);

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
        ImageView gameImage = findViewById(R.id.iv_game_on_game_detail_page);
        TextView gameDescription = findViewById(R.id.tv_description_game_on_game_detail_page);
        Button reviewGameButton = findViewById(R.id.button_add_review_game_detail);

        userHelper.open();
        User user = userHelper.findUser(getIntent().getStringExtra("username"));
        userHelper.close();

        placeHelper.open();
        Place place = placeHelper.findPlace(getIntent().getIntExtra("placeId", 1));
        placeHelper.close();

        gameName.setText(place.getName());
        gameGenre.setText(place.getGenre());
        gameRating.setText(String.valueOf(place.getRating()));
        gamePrice.setText(place.getPrice());
        gameDescription.setText(place.getDescription());

        new ImageLoaderTask(gameImage).execute(place.getImage());

        reviewGameButton.setOnClickListener(view -> {
            wishHelper.open();
            if(wishHelper.isExistWish(user.getId(), place.getId())){
                Toast.makeText(GamesDetailActivity.this, "You have already added this place to wishlist!", Toast.LENGTH_SHORT).show();
                wishHelper.close();
            } else{
                wishHelper.insertWish(new Wish(null, user.getId(), place.getId()));
                wishHelper.close();
                Toast.makeText(GamesDetailActivity.this, "Wishlist added!", Toast.LENGTH_SHORT).show();
                Intent intentHome = new Intent(GamesDetailActivity.this, HomePageActivity.class);
                intentHome.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intentHome);
            }
        });
    }
}
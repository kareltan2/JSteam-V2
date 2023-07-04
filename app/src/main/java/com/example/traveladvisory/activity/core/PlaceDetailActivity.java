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
public class PlaceDetailActivity extends AppCompatActivity {

    private final WishHelper wishHelper = new WishHelper(this);

    private final UserHelper userHelper = new UserHelper(this);

    private final PlaceHelper placeHelper = new PlaceHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_place_detail);

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
        TextView placeName = findViewById(R.id.tv_title_place_on_place_detail_page);
        TextView placeGenre = findViewById(R.id.tv_genre_name_place_on_place_detail_page);
        TextView placeRating = findViewById(R.id.tv_rating_value_place_on_place_detail_page);
        TextView placePrice = findViewById(R.id.tv_price_value_place_on_place_detail_page);
        ImageView placeImage = findViewById(R.id.iv_place_on_place_detail_page);
        TextView placeDescription = findViewById(R.id.tv_description_place_on_place_detail_page);
        Button wishPlaceButton = findViewById(R.id.button_add_wish_place_detail);

        userHelper.open();
        User user = userHelper.findUser(getIntent().getStringExtra("username"));
        userHelper.close();

        placeHelper.open();
        Place place = placeHelper.findPlace(getIntent().getIntExtra("placeId", 1));
        placeHelper.close();

        placeName.setText(place.getName());
        placeGenre.setText(place.getGenre());
        placeRating.setText(String.valueOf(place.getRating()));
        placePrice.setText(place.getPrice());
        placeDescription.setText(place.getDescription());

        new ImageLoaderTask(placeImage).execute(place.getImage());

        wishPlaceButton.setOnClickListener(view -> {
            wishHelper.open();
            if(wishHelper.isExistWish(user.getId(), place.getId())){
                Toast.makeText(PlaceDetailActivity.this, "You have already added this place to wishlist!", Toast.LENGTH_SHORT).show();
                wishHelper.close();
            } else {
                wishHelper.insertWish(new Wish(null, user.getId(), place.getId()));
                wishHelper.close();
                Toast.makeText(PlaceDetailActivity.this, "Wishlist added!", Toast.LENGTH_SHORT).show();
                Intent intentHome = new Intent(PlaceDetailActivity.this, HomePageActivity.class);
                intentHome.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intentHome);
            }
        });
    }
}
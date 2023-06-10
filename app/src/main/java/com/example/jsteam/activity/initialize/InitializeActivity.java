package com.example.jsteam.activity.initialize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsteam.R;
import com.example.jsteam.activity.prelogin.MainActivity;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.ReviewHelper;
import com.example.jsteam.model.dao.Game;
import com.example.jsteam.model.dao.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * @author kareltan
 */
public class InitializeActivity extends AppCompatActivity {

    private final GameHelper gameHelper = new GameHelper(this);

    private final ReviewHelper reviewHelper = new ReviewHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        gameHelper.open();
        reviewHelper.open();
        if (gameHelper.findAllGame().isEmpty() && reviewHelper.findAllReview().isEmpty()){
            fetchAndStoreData();
        }
        reviewHelper.close();
        gameHelper.close();

        Intent intent = new Intent(InitializeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void fetchAndStoreData() {
        String[] reviews = {
                "Great product!",
                "Terrible experience.",
                "Highly recommended!",
                "Not worth the price.",
                "Average quality.",
                "Exceptional service!",
                "Disappointing performance.",
                "Best purchase ever!",
                "Could be better.",
                "Outstanding value."
        };
        Random random = new Random();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://mocki.io/v1/54b65ff1-7c3d-4b3d-aa77-3c16085991cd";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, response -> {
            try {
                JSONArray gamesArray = response.getJSONArray("games");

                for (int i = 1; i <= gamesArray.length(); i++) {
                    JSONObject jsonObject = gamesArray.getJSONObject(i-1);
                    String name = jsonObject.getString("name");
                    String genre = jsonObject.getString("genre");
                    Double rating = jsonObject.getDouble("rating");
                    String price = jsonObject.getString("price");
                    String image = jsonObject.getString("image");
                    String description = jsonObject.getString("description");

                    gameHelper.open();
                    gameHelper.insertGame(new Game(null, name, genre, rating, price, image, description));
                    gameHelper.close();

                    for (int j = 1; j <= 3; j++) {
                        int randomIndex = random.nextInt(reviews.length);
                        String randomReview = reviews[randomIndex];

                        reviewHelper.open();
                        reviewHelper.insertReview(new Review(null, j, i, randomReview));
                        reviewHelper.close();
                    }
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Toast.makeText(this, "Error Occurred When Retrieving JSON", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);
    }
}
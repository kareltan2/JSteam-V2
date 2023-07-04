package com.example.traveladvisory.activity.initialize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.traveladvisory.R;
import com.example.traveladvisory.activity.prelogin.MainActivity;
import com.example.traveladvisory.helper.PlaceHelper;
import com.example.traveladvisory.helper.WishHelper;
import com.example.traveladvisory.model.dao.Place;
import com.example.traveladvisory.model.dao.Wish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * @author kareltan
 */
public class InitializeActivity extends AppCompatActivity {

    private final PlaceHelper placeHelper = new PlaceHelper(this);

    private final WishHelper wishHelper = new WishHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        placeHelper.open();
        wishHelper.open();
        if (placeHelper.findAllPlace().isEmpty() && wishHelper.findAllWish().isEmpty()){
            fetchAndStoreData();
        }
        wishHelper.close();
        placeHelper.close();

        Intent intent = new Intent(InitializeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void fetchAndStoreData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://mocki.io/v1/967bccbf-14b7-4849-bbaf-bbd4f1a6765b";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, response -> {
            try {
                JSONArray placeArray = response.getJSONArray("places");

                placeHelper.open();
                for (int i = 1; i <= placeArray.length(); i++) {
                    JSONObject jsonObject = placeArray.getJSONObject(i-1);
                    String name = jsonObject.getString("name");
                    String genre = jsonObject.getString("genre");
                    Double rating = jsonObject.getDouble("rating");
                    String price = jsonObject.getString("price");
                    String image = jsonObject.getString("image");
                    String description = jsonObject.getString("description");

                    placeHelper.insertPlace(new Place(null, name, genre, rating, price, image, description));
                }
                placeHelper.close();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Toast.makeText(this, "Error Occurred When Retrieving JSON", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);
    }
}
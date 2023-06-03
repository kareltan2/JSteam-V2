package com.example.jsteam.activity.initialize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.jsteam.activity.prelogin.MainActivity;
import com.example.jsteam.R;
import com.example.jsteam.model.DatabaseConfiguration;

/**
 * @author kareltan
 */
public class InitializeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);
        testingConfiguration();
        Intent intent = new Intent(InitializeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void testingConfiguration() {
        //testing purpose
        //user
        DatabaseConfiguration.DatabaseUser(0, "kareltan", "pintar1", "karel.tan1@gmail.com", "INDO", "082325124125");
        DatabaseConfiguration.DatabaseUser(1, "jason", "pintar2", "jason@gmail.com", "INDO", "082325124123");
        DatabaseConfiguration.DatabaseUser(2, "niclauss", "pintar3", "niclauss@gmail.com", "INDO", "082325124122");

        //game
        DatabaseConfiguration.DatabaseGame("Mobile Legend", "War Games Explorer", 4.9, 90000, "A war games with free superior hero skin");
        DatabaseConfiguration.DatabaseGame("League of Legend", "War Games Explorer", 4.9, 90000, "A war games with free superior hero skin");
        DatabaseConfiguration.DatabaseGame("Clash of Clans", "War Games Explorer", 4.9, 90000, "A war games with free superior hero skin");
        DatabaseConfiguration.DatabaseGame("Among Us", "Tactical Games", 4.9, 1000000, "A tactical games and multiplayer game");

        //review
        DatabaseConfiguration.DatabaseReview("Mobile Legend", "a very good game with extraordinary experience and UI", "kareltan1");
        DatabaseConfiguration.DatabaseReview("Clash of Clans", "mantull", "kareltan1");
        DatabaseConfiguration.DatabaseReview("Among Us", "game keren", "kareltan1");

        DatabaseConfiguration.DatabaseReview("Mobile Legend", "GG", "kareltan2");
        DatabaseConfiguration.DatabaseReview("Clash of Clans", "mantullita", "kareltan2");
        DatabaseConfiguration.DatabaseReview("Among Us", "kelasss brader", "kareltan2");

        DatabaseConfiguration.DatabaseReview("Mobile Legend", "BEK BEK BEK", "kareltan3");
        DatabaseConfiguration.DatabaseReview("Clash of Clans", "TIUNG", "kareltan3");
        DatabaseConfiguration.DatabaseReview("Among Us", "kelasss brader", "kareltan3");

    }
}
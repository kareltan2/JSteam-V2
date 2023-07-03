package com.example.traveladvisory.activity.core;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.traveladvisory.R;
import com.example.traveladvisory.databinding.ActivityProfilePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author kareltan
 */
public class ProfilePageActivity extends AppCompatActivity {

    private ActivityProfilePageBinding binding;

    private Switch darkModeToggle;

    private SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String DARK_MODE = "darkMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_game_page, R.id.navigation_review_section, R.id.navigation_profile_section)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_profile_page);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewProfilePage, navController);
    }

    private void setDarkModeEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DARK_MODE, enabled);
        editor.apply();

        int nightMode = enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    private boolean isDarkModeEnabled() {
        return sharedPreferences.getBoolean(DARK_MODE, false);
    }
}
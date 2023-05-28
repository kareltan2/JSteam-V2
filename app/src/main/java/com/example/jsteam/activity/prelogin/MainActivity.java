package com.example.jsteam.activity.prelogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsteam.R;
import com.example.jsteam.activity.core.HomePageActivity;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.dao.User;

import java.util.Objects;

/**
 * @author kareltan
 */
public class MainActivity extends AppCompatActivity {

    private final UserHelper userHelper = new UserHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        userHelper.open();
        Button loginButton = findViewById(R.id.button_login_login_page);
        TextView notHaveAccountText = findViewById(R.id.tv_didnt_have_account);
        EditText usernameLoginPage = findViewById(R.id.pt_username_login);
        EditText passwordLoginPage = findViewById(R.id.pt_password_login);

        loginButton.setOnClickListener(view -> {
            String username = String.valueOf(usernameLoginPage.getText());
            String password = String.valueOf(passwordLoginPage.getText());

            if(validationNotEmpty(username, password)){
                validationAccount(username, password);
            }

        });

        notHaveAccountText.setOnClickListener(view -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

    }

    private boolean validationNotEmpty(String username, String password) {
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Email or password cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validationAccount(String username, String password) {
        if(Objects.nonNull(userHelper.findUser(username))){
            User user = userHelper.findUser(username);

            if(!user.getPassword().equals(password)){
                Toast.makeText(MainActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                Toast.makeText(MainActivity.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                Intent intentHome = new Intent(MainActivity.this, HomePageActivity.class);
                intentHome.putExtra("username", user.getUsername());
                startActivity(intentHome);
                return true;
            }
        }

        Toast.makeText(MainActivity.this, "Unregistered User! Please register first!", Toast.LENGTH_SHORT).show();
        userHelper.close();
        return false;
    }

}
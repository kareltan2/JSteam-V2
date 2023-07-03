package com.example.traveladvisory.activity.prelogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveladvisory.R;
import com.example.traveladvisory.helper.UserHelper;
import com.example.traveladvisory.model.dao.User;

/**
 * @author kareltan
 */
public class RegisterActivity extends AppCompatActivity {

    private final UserHelper userHelper = new UserHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        Button registerButton = findViewById(R.id.button_register_register_page);
        TextView hasAccount = findViewById(R.id.tv_already_has_account);
        EditText usernameRegisterPage = findViewById(R.id.pt_username_register);
        EditText emailRegisterPage = findViewById(R.id.pt_email_register);
        EditText regionRegisterPage = findViewById(R.id.pt_region_register);
        EditText phoneRegisterPage = findViewById(R.id.pt_phone_number_register);
        EditText passwordRegisterPage = findViewById(R.id.pt_input_password_register);

        registerButton.setOnClickListener(view -> {
            String username = String.valueOf(usernameRegisterPage.getText());
            String email = String.valueOf(emailRegisterPage.getText());
            String region = String.valueOf(regionRegisterPage.getText());
            String phoneNumber = String.valueOf(phoneRegisterPage.getText());
            String password = String.valueOf(passwordRegisterPage.getText());

            if(validationNotEmpty(username, email, region, phoneNumber, password)){
                if(validateUniqueUsername(username)){
                    if(validateAlphanumeric(username)){
                        if(validatePasswordLength(password)){
                            if(validateAlphanumeric(password)){
                                if(validateEmail(email)){
                                    if(validatePhoneNumber(phoneNumber)){
                                        userHelper.open();
                                        userHelper.insertUser(new User(null, username, password, email, region, phoneNumber));
                                        userHelper.close();

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });

        hasAccount.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateEmail(String email) {
        if(!email.endsWith(".com") || !email.contains("@")){
            Toast.makeText(RegisterActivity.this, "Email Address must end with '.com' and has '@'!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateUniqueUsername(String username) {
        userHelper.open();
        if(userHelper.isExistUsername(username)){
            Toast.makeText(RegisterActivity.this, "This Username has already registered!", Toast.LENGTH_SHORT).show();
            return false;
        }
        userHelper.close();
        return true;
    }

    private boolean validationNotEmpty(String username, String email, String region, String phone, String password) {
        if(username.isEmpty() || email.isEmpty() || region.isEmpty() || phone.isEmpty() || password.isEmpty()){
            Toast.makeText(RegisterActivity.this, "All Field must be filled!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateAlphanumeric(String string) {
        if(!checkLetterExist(string) || !checkNumericExist(string)){
            Toast.makeText(RegisterActivity.this, "Username and Password must be alphanumeric!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkLetterExist(String string){
        char[] passwordArray = string.toCharArray();
        boolean statusWordExist = false;

        for(char c : passwordArray){
            if(Character.isLetter(c)){
                statusWordExist = true;
                break;
            }
        }
        return statusWordExist;
    }

    private boolean checkNumericExist(String string){
        char[] passwordArray = string.toCharArray();
        boolean statusNumericExist = false;

        for(char c : passwordArray){
            if(Character.isDigit(c)){
                statusNumericExist = true;
                break;
            }
        }
        return statusNumericExist;
    }

    private boolean validatePasswordLength(String password) {
        if(password.length() < 5){
            Toast.makeText(RegisterActivity.this, "Password not valid. Please input password with length at least 5!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if(checkLetterExist(phoneNumber)){
            Toast.makeText(RegisterActivity.this, "Phone Number must be number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
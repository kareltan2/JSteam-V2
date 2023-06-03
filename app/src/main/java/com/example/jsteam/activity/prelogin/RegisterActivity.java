package com.example.jsteam.activity.prelogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.jsteam.Manifest;
import android.Manifest;
import com.example.jsteam.activity.core.OTPVerification;
import com.example.jsteam.model.DatabaseConfiguration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jsteam.R;

import java.util.ArrayList;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        Button registerButton = findViewById(R.id.button_register_register_page);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    SmsManager smsManager = SmsManager.getDefault();
                    EditText phoneRegisterPage = findViewById(R.id.pt_phone_number_register);
                    EditText emailRegisterPage = findViewById(R.id.pt_email_register);
                    Random random = new Random();
                    int randomNumber = random.nextInt(10);
                    int randomNumber2 = random.nextInt(10);
                    int randomNumber3 = random.nextInt(10);
                    int randomNumber4 = random.nextInt(10);
                    String otp = Integer.toString(randomNumber) + Integer.toString(randomNumber2)+Integer.toString(randomNumber3
                    )+Integer.toString(randomNumber4);
                    ArrayList<String> parts = smsManager.divideMessage(otp + " is the otp");
                    String phone = phoneRegisterPage.getText().toString();
                    smsManager.sendMultipartTextMessage(phone, null, parts, null, null);

                    Intent intent = new Intent(RegisterActivity.this, OTPVerification.class);
                    intent.putExtra("mobile", phoneRegisterPage.getText().toString());
                    intent.putExtra("email", emailRegisterPage.getText().toString());
                    intent.putExtra("otp", otp);
                    startActivity(intent);
                }else{
                    ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EditText phoneRegisterPage = findViewById(R.id.pt_phone_number_register);
                SmsManager smsManager = SmsManager.getDefault();
                Random random = new Random();
                int randomNumber = random.nextInt(10);
                int randomNumber2 = random.nextInt(10);
                int randomNumber3 = random.nextInt(10);
                int randomNumber4 = random.nextInt(10);
                String otp = Integer.toString(randomNumber) + Integer.toString(randomNumber2)+Integer.toString(randomNumber3
                )+Integer.toString(randomNumber4);
                ArrayList<String> parts = smsManager.divideMessage(otp + " is the otp");
                String phoneNumber = phoneRegisterPage.getText().toString();
                smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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
                                        DatabaseConfiguration.DatabaseUser(DatabaseConfiguration.users.size() + 1, username, password, email, region, phoneNumber);

//                                        Toast.makeText(RegisterActivity.this, "Ke OTP", Toast.LENGTH_SHORT).show();

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
        if(!DatabaseConfiguration.findIndexUser(username).equals(-1)){
            Toast.makeText(RegisterActivity.this, "This Username has already registered!", Toast.LENGTH_SHORT).show();
            return false;
        }
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
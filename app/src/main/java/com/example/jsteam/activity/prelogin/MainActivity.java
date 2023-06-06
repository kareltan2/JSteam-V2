package com.example.jsteam.activity.prelogin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import com.example.jsteam.R;
import com.example.jsteam.activity.core.HomePageActivity;
import com.example.jsteam.activity.core.MapsActivity;
import com.example.jsteam.activity.core.OTPVerification;
import com.example.jsteam.deprecated.DatabaseConfiguration;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.dao.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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

//        Button registerButton = findViewById(R.id.button_login_login_page);
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
//                    userHelper.open();
//                    EditText usernameLoginPage = findViewById(R.id.pt_username_login);
//                    User user = userHelper.findUser(String.valueOf(usernameLoginPage.getText().toString()));
//                    SmsManager smsManager = SmsManager.getDefault();
//                    Toast.makeText(MainActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(10);
//                    int randomNumber2 = random.nextInt(10);
//                    int randomNumber3 = random.nextInt(10);
//                    int randomNumber4 = random.nextInt(10);
//                    String otp = Integer.toString(randomNumber) + Integer.toString(randomNumber2)+Integer.toString(randomNumber3
//                    )+Integer.toString(randomNumber4);
//                    ArrayList<String> parts = smsManager.divideMessage(otp + " is the otp");
//                    String phone = user.getPhoneNumber();
//                    String email = user.getEmail();
//
//                    smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
//
//                    Intent intent = new Intent(MainActivity.this, OTPVerification.class);
//                    intent.putExtra("mobile", phone);
//                    intent.putExtra("email", email);
//                    intent.putExtra("otp", otp);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(MainActivity.this, "ke otp else", Toast.LENGTH_SHORT).show();
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
//                }
//            }
//        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                EditText phoneRegisterPage = findViewById(R.id.pt_phone_number_register);
//                SmsManager smsManager = SmsManager.getDefault();
//                Random random = new Random();
//                int randomNumber = random.nextInt(10);
//                int randomNumber2 = random.nextInt(10);
//                int randomNumber3 = random.nextInt(10);
//                int randomNumber4 = random.nextInt(10);
//                String otp = Integer.toString(randomNumber) + Integer.toString(randomNumber2)+Integer.toString(randomNumber3
//                )+Integer.toString(randomNumber4);
//                ArrayList<String> parts = smsManager.divideMessage(otp + " is the otp");
//                String phoneNumber = phoneRegisterPage.getText().toString();
//                smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
//            } else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void init(){
        userHelper.open();
        AtomicInteger index = new AtomicInteger();
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
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    userHelper.open();
                    EditText usernameLoginPage = findViewById(R.id.pt_username_login);
                    User user2 = userHelper.findUser(String.valueOf(usernameLoginPage.getText().toString()));
                    SmsManager smsManager = SmsManager.getDefault();
                    Random random = new Random();
                    int randomNumber = random.nextInt(10);
                    int randomNumber2 = random.nextInt(10);
                    int randomNumber3 = random.nextInt(10);
                    int randomNumber4 = random.nextInt(10);
                    String otp = Integer.toString(randomNumber) + Integer.toString(randomNumber2)+Integer.toString(randomNumber3
                    )+Integer.toString(randomNumber4);
                    ArrayList<String> parts = smsManager.divideMessage(otp + " is the otp");
                    String phone = user2.getPhoneNumber();
                    String email = user2.getEmail();

                    smsManager.sendMultipartTextMessage(phone, null, parts, null, null);

                    Intent intent = new Intent(MainActivity.this, OTPVerification.class);
                    intent.putExtra("mobile", phone);
                    intent.putExtra("email", email);
                    intent.putExtra("otp", otp);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "ke otp else", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
                return true;
            }
        }

        Toast.makeText(MainActivity.this, "Unregistered User! Please register first!", Toast.LENGTH_SHORT).show();
        userHelper.close();
        return false;
    }

}
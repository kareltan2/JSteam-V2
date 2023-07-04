package com.example.traveladvisory.activity.prelogin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.traveladvisory.R;
import com.example.traveladvisory.helper.UserHelper;
import com.example.traveladvisory.model.dao.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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
        Button loginButton = findViewById(R.id.button_login_login_page);
        TextView notHaveAccountText = findViewById(R.id.tv_didnt_have_account);
        EditText usernameLoginPage = findViewById(R.id.pt_username_login);
        EditText passwordLoginPage = findViewById(R.id.pt_password_login);

        loginButton.setOnClickListener(view -> {
            String username = String.valueOf(usernameLoginPage.getText());
            String password = String.valueOf(passwordLoginPage.getText());

            if(validationNotEmpty(username, password)){
                userHelper.open();
                validationAccount(username, password);
                userHelper.close();
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
                    EditText inputtedUsername = findViewById(R.id.pt_username_login);
                    User userDB = userHelper.findUser(inputtedUsername.getText().toString());
                    SmsManager smsManager = SmsManager.getDefault();
                    Random random = new Random();
                    int OTPFirstNumber = random.nextInt(10);
                    int OTPSecondNumber = random.nextInt(10);
                    int OTPThirdNumber = random.nextInt(10);
                    int OTPFourthNumber = random.nextInt(10);
                    String OTP = String.format("%d%d%d%d", OTPFirstNumber, OTPSecondNumber, OTPThirdNumber, OTPFourthNumber);
                    ArrayList<String> parts = smsManager.divideMessage("This is the OTP Code for TravelAdvisory\n" + OTP + "\nDo not share the OTP to anyone!");
                    String phone = userDB.getPhoneNumber();
                    String email = userDB.getEmail();

                    smsManager.sendMultipartTextMessage(phone, null, parts, null, null);

                    Intent intent = new Intent(MainActivity.this, OTPVerification.class);
                    intent.putExtra("phoneNumber", phone);
                    intent.putExtra("email", email);
                    intent.putExtra("otp", OTP);
                    intent.putExtra("username", user.getUsername());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please Update Your Personal Setting About Receiving SMS!", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
                return true;
            }
        }

        Toast.makeText(MainActivity.this, "Unregistered User! Please register first!", Toast.LENGTH_SHORT).show();
        return false;
    }

}
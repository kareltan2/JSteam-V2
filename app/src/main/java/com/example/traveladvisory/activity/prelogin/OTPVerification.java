package com.example.traveladvisory.activity.prelogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveladvisory.R;
import com.example.traveladvisory.activity.core.HomePageActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author kareltan
 */
public class OTPVerification extends AppCompatActivity {

    private EditText otpEt1, otpEt2, otpEt3, otpEt4;
    private TextView resendBtn;

    private boolean resendEnabled = false;
    private int resendTime = 20;

    private int selectETPosition = 0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        otpEt1 = findViewById(R.id.otpET1);
        otpEt2 = findViewById(R.id.otpET2);
        otpEt3 = findViewById(R.id.otpET3);
        otpEt4 = findViewById(R.id.otpET4);

        resendBtn = findViewById(R.id.resendBtn);

        final Button verifyBtn = findViewById(R.id.verifyBtn);
        final TextView otpEmail = findViewById(R.id.otpEmail);
        final TextView otpMobile = findViewById(R.id.otpMobile);

        final String getEmail = getIntent().getStringExtra("email");
        final String getMobile = getIntent().getStringExtra("phoneNumber");

        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);

        showKeyboard(otpEt1);

        startCountDownTimer();

        AtomicReference<String> otp = new AtomicReference<>(getIntent().getStringExtra("otp"));

        resendBtn.setOnClickListener(view -> {
            if(resendEnabled){
                SmsManager smsManager = SmsManager.getDefault();
                Random random = new Random();
                int randomNumber = random.nextInt(10);
                int randomNumber2 = random.nextInt(10);
                int randomNumber3 = random.nextInt(10);
                int randomNumber4 = random.nextInt(10);
                otp.set(String.format("%d%d%d%d", randomNumber, randomNumber2, randomNumber3, randomNumber4));
                ArrayList<String> parts = smsManager.divideMessage("This is the OTP Code for TravelAdvisory\n" + otp + "\nDo not share the OTP to anyone!");
                String phone = getIntent().getStringExtra("phoneNumber");

                smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
                Toast.makeText(OTPVerification.this, "New OTP has been sent", Toast.LENGTH_SHORT).show();

                startCountDownTimer();
            }
        });

        verifyBtn.setOnClickListener(view -> {
            final String inputOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();

            if(inputOtp.equals(otp.toString())){
                Intent intent = new Intent(OTPVerification.this, HomePageActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
            else{
                Toast.makeText(OTPVerification.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showKeyboard(EditText otpET){
        otpET.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);

    }

    private void startCountDownTimer(){
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                resendBtn.setText("Resend Code (" + seconds + ")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
            }
        }.start();
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() > 0){
                if(selectETPosition == 0){
                    selectETPosition = 1;
                    showKeyboard(otpEt2);
                }
                else if(selectETPosition == 1){
                    selectETPosition = 2;
                    showKeyboard(otpEt3);
                }
                else if(selectETPosition == 2){
                    selectETPosition = 3;
                    showKeyboard(otpEt4);
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectETPosition == 3){
                selectETPosition=2;
                showKeyboard(otpEt1);
            }
            else if(selectETPosition == 2){
                selectETPosition=1;
                showKeyboard(otpEt2);
            }
            else if(selectETPosition == 1){
                selectETPosition=0;
                showKeyboard(otpEt3);
            }

            return true;
        }

        else{
            return super.onKeyUp(keyCode, event);
        }
    }
}
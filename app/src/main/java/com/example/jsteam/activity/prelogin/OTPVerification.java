package com.example.jsteam.activity.prelogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsteam.R;
import com.example.jsteam.activity.core.HomePageActivity;

public class OTPVerification extends AppCompatActivity {

    private EditText otpEt1, otpEt2, otpEt3, otpEt4;
    private TextView resendBtn;

    private boolean resendEnabled = false;
    private int resendTime = 60;

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
        final String getMobile = getIntent().getStringExtra("mobile");

        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);

        showKeyboard(otpEt1);

        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled){
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();


                if(generateOtp.equals(getIntent().getStringExtra("otp"))){
                    Intent intent = new Intent(OTPVerification.this, HomePageActivity.class);
                    intent.putExtra("username", getIntent().getStringExtra("username"));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(OTPVerification.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
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

        new CountDownTimer(resendTime * 60, 100){


            @Override
            public void onTick(long l) {
                resendBtn.setText("resent Code ("+(l/60)+")");
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
package com.example.cinetime_nepal.member.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.utils.Validator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView signupTv, emailEt, pwdEt;
    Button signinBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();
        clickListener();
    }

    private void initVar() {
        emailEt = findViewById(R.id.email_et);
        pwdEt = findViewById(R.id.pwd_et);
        signupTv = findViewById(R.id.signup_tv);
        signinBtn = findViewById(R.id.signin_btn);
    }

    private void clickListener() {
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), SigninActivity.class);
//                startActivity(intent);
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = pwdEt.getText().toString();
                if (!Validator.isEmailValid(email)) {
                    emailEt.setError("Invalid email address");
                } else if (password.isEmpty()) {
                    pwdEt.setError("please enter password");
                } else if (password.length() < 6) {
                    pwdEt.setError("password must be greater than 6 character");
                } else {
                    //code to open fragment from another fragment
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.profile_f, new UProfileFragment()); //My second Fragment
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                }
            }
        });
    }
}

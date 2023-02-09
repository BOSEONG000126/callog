package org.techtown.callog;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_login extends AppCompatActivity {

        TextView sign;
        Button login;

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            setContentView(R.layout.login);

            //회원가입 버튼
            sign = findViewById(R.id.signin);

            //회원가입 버튼 클릭시, 회원가입 페이지로 이동
            sign.setOnClickListener(v -> {
                Intent intent = new Intent(this, Signup.class);
                startActivity(intent);
            });
            //로그인 버튼
            login = findViewById(R.id.loginbutton);

            login.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        }
    }

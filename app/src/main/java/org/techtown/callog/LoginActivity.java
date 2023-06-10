package org.techtown.callog;


import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

        private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
        private DatabaseReference mDatabaseRef; //실시간 데이터베이스

        EditText signemail;
        EditText signpw;
        TextView sign;
        Button login;



        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            mFirebaseAuth = FirebaseAuth.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("callog");

            signemail = findViewById(R.id.editID);
            signpw = findViewById(R.id.editPassword);

            sign = findViewById(R.id.signin);
            login = findViewById(R.id.loginbutton);



            //페이지로 이동
            sign.setOnClickListener(v -> {
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
            });
            login.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String email = signemail.getText().toString();
                    String pw = signpw.getText().toString();

                    mFirebaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }


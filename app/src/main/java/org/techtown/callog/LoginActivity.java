package org.techtown.callog;

import static android.app.PendingIntent.getActivity;

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

public class LoginActivity extends AppCompatActivity {

        int version = 1;
        DatabaseOpenHelper helper;
        SQLiteDatabase database;

        EditText editID;
        EditText editPassword;
        TextView sign;
        Button login;

        String sql;
        Cursor cursor;

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            editID = findViewById(R.id.editID);
            editPassword = findViewById(R.id.editPassword);

            sign = findViewById(R.id.signin);
            login = findViewById(R.id.loginbutton);

            helper = new DatabaseOpenHelper(LoginActivity.this, DatabaseOpenHelper.tableName, null, version);
            database = helper.getWritableDatabase();

            //페이지로 이동
            sign.setOnClickListener(v -> {
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
            });
            login.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String id = editID.getText().toString();
                    String pw = editPassword.getText().toString();

                    if(id.length() == 0 || pw.length() == 0) {
                        //아이디와 비밀번호는 필수 입력사항입니다.
                        Toast toast = Toast.makeText(LoginActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                    cursor = database.rawQuery(sql, null);

                    if(cursor.getCount() != 1){
                        //아이디가 틀렸습니다.
                        Toast toast = Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    sql = "SELECT pw FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                    cursor = database.rawQuery(sql, null);

                    cursor.moveToNext();
                    if(!pw.equals(cursor.getString(0))){
                        //비밀번호가 틀렸습니다.
                        Toast toast = Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        //로그인성공
                        Toast toast = Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT);
                        toast.show();
                        //인텐트 생성 및 호출
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    cursor.close();
                }
            });
        }
    }


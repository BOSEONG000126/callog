package org.techtown.callog;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    TextView back;
    EditText signname,signid,signpw,singpw2,signemail,signbirthyear,signbirthdate,signbirthday;
    Button pwcheck, submit;

    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );

        //기입 항목
        signname = findViewById(R.id.signName);
        signid=findViewById(R.id.signID);
        signpw=findViewById(R.id.signPW);
        singpw2=findViewById(R.id.signPW2);
        signemail=findViewById(R.id.signmail);
        signbirthyear=findViewById(R.id.signBirth);
        signbirthdate=findViewById(R.id.signBirth2);
        signbirthday=findViewById(R.id.signBirth3);

        helper = new DatabaseOpenHelper(SignupActivity.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        //비밀번호 확인 버튼
        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(v -> {
            if(signpw.getText().toString().equals(singpw2.getText().toString())){
                pwcheck.setText("일치");
            }else{
                Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        //회원가입 완료 버튼
        submit = findViewById(R.id.signupbutton);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String id = signid.getText().toString();
                String pw = signpw.getText().toString();
                String name = signname.getText().toString();
                String birthyear = signbirthyear.getText().toString();
                String birthdate = signbirthdate.getText().toString();
                String birthday = signbirthday.getText().toString();
                String email = signemail.getText().toString();

                if(id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast toast = Toast.makeText(SignupActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 0){
                    //존재하는 아이디입니다.
                    Toast toast = Toast.makeText(SignupActivity.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    helper.insertUser(database,name,id,birthyear,birthdate,birthday,pw,email);
                    Toast toast = Toast.makeText(SignupActivity.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

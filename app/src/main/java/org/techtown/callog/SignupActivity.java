package org.techtown.callog;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스

    TextView back;
    EditText signname,signpw,singpw2,signemail,signbirth1,signbirth2,signbirth3; //회원가입 입력필드
    Button pwcheck, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("callog");

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );

        //기입 항목
        signname = findViewById(R.id.signName);
        signemail=findViewById(R.id.signEmail);
        signpw=findViewById(R.id.signPW);
        singpw2=findViewById(R.id.signPW2);
        signbirth1=findViewById(R.id.signBirth);
        signbirth2=findViewById(R.id.signBirth2);
        signbirth3=findViewById(R.id.signBirth3);

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
                String singemail = signemail.getText().toString();
                String singpw = signpw.getText().toString();
                String name = signname.getText().toString();
                String birth1 = signbirth1.getText().toString();
                String birth2 = signbirth2.getText().toString();
                String birth3 = signbirth3.getText().toString();

                //파이어베이스 진행
                mFirebaseAuth.createUserWithEmailAndPassword(singemail,singpw).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account =new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setName(name);
                            account.setSignbirth1(birth1);
                            account.setSignbirth2(birth2);
                            account.setSignbirth3(birth3);
                            account.setPassword(singpw);

                            //setValue : 데이터베이스에 insert 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "회원가입에 실패하셨습니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

package org.techtown.callog;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homefragment;
    ChecklistFragment checklistfragment;
    MypageFragment mypagefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homefragment = new HomeFragment();
        checklistfragment = new ChecklistFragment();
        mypagefragment = new MypageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homefragment).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(), "첫 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, homefragment).commit();

                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "두 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, checklistfragment).commit();

                        return true;
                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(), "세 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, mypagefragment).commit();

                        return true;
                }

                return false;
            }
        });
    }
}
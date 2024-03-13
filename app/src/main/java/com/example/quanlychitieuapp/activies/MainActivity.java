package com.example.quanlychitieuapp.activies;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;
import com.example.quanlychitieuapp.fragments.SettingFragment;
import com.example.quanlychitieuapp.fragments.StatisticsFragment;
import com.example.quanlychitieuapp.fragments.TransactionFragment;
import com.example.quanlychitieuapp.fragments.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_item_1) {
                    // Handle Home Fragment selection
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new TransactionFragment())
                            .commit();
                } else if (id == R.id.nav_item_2) {
                    // Handle Settings Fragment selection
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new WalletFragment())
                            .commit();
                } else if (id == R.id.nav_item_3) {
                    // Handle Settings Fragment selection
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new StatisticsFragment())
                            .commit();
                } else if (id == R.id.nav_item_4) {
                    // Handle Settings Fragment selection
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new SettingFragment())
                            .commit();
                }

                return true;
            }
        });
    }
}

package com.example.MDP;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;

import com.example.MDP.Fragment.NowPlaying;
import com.example.MDP.Fragment.Profile;
import com.example.MDP.Fragment.Upcoming;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.now_playing:
                    fragment = new NowPlaying();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.upcoming:
                    fragment = new Upcoming();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.profile:
                    fragment = new Profile();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null){
            navigation.setSelectedItemId(R.id.now_playing);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}

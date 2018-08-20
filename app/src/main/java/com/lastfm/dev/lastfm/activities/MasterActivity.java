package com.lastfm.dev.lastfm.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lastfm.dev.lastfm.R;
import com.lastfm.dev.lastfm.fragments.MainScreenFragment;


public class MasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        // add main fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MainScreenFragment mainScreenFragment = new MainScreenFragment();
        fragmentTransaction.add(R.id.container, mainScreenFragment);
        fragmentTransaction.commit();
    }
}

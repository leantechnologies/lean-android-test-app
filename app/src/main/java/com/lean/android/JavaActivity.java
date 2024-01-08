package com.lean.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView1, JavaFragment.newInstance())
                .commit();
    }
}
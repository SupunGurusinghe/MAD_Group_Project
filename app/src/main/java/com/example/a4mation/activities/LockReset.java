package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a4mation.R;

public class LockReset extends AppCompatActivity {

    private ImageView imageBack;
    private Button setKeyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_reset);


        //come back
        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LockReset.this, LockMain.class)
                );
            }
        });

        //reset key button
        setKeyButton = findViewById(R.id.setKeyButton);
        setKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LockReset.this, LockTwo.class)
                );
            }
        });
    }
}
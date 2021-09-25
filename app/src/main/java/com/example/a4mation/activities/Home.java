package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a4mation.R;

public class Home extends AppCompatActivity {

    private LinearLayout layoutPasswordMain,layoutTodoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        layoutPasswordMain = findViewById(R.id.layoutPasswordMain);
        layoutPasswordMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Home.this, PasswordMain.class)
                );
            }
        });

        layoutTodoHome = findViewById(R.id.layoutTodoHome);
        layoutTodoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Home.this, TodoHome.class)
                );
            }
        });

    }
}
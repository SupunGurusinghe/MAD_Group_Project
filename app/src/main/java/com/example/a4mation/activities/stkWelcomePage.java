package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.a4mation.R;

public class stkWelcomePage extends AppCompatActivity {
Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_welcome_page);


        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();



// Define ColorDrawable object and parse color
// using parseColor method
// with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#669900"));





// Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);



// Change Toolbar text
        getSupportActionBar().setTitle("Sticky Notes");
// getSupportActionBar().setSubtitle("Main");



// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorNew));
        }


        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stkWelcomePage.this, stkAfterType.class));
            }
        });
    }
}
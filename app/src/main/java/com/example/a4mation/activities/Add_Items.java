package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.a4mation.R;

public class Add_Items extends AppCompatActivity {

    EditText item_input,quentity_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

// Define ColorDrawable object and parse color
// using parseColor method
// with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF6347"));

// Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

// Change Toolbar text
        getSupportActionBar().setTitle("Add Items");
// getSupportActionBar().setSubtitle("Main");


// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.listItemStatusBar));
        }

        item_input = findViewById(R.id.item_input);
        quentity_input = findViewById(R.id.quentity_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            DbHandler myDB = new DbHandler(Add_Items.this);
            myDB.addItems(item_input.getText().toString().trim(),
                    Integer.valueOf(quentity_input.getText().toString().trim()));

        });
    }
}
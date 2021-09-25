package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.a4mation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class stkAfterType extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    DbHandler mid;
    ArrayList<String> ID, Title, Timestamp, Colorr, Body, Image;
    customAdapter cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_after_type);


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
        getSupportActionBar().setTitle("All Sticky Notes");
// getSupportActionBar().setSubtitle("Main");



// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorNew));
        }
        
        recyclerView = findViewById(R.id.stkRecyclerView);
        fab = findViewById(R.id.bt_float);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stkAfterType.this, stkTypeMsg.class));
            }
        });
        mid = new DbHandler(stkAfterType.this);
        ID = new ArrayList<>();
        Title = new ArrayList<>();
        Timestamp = new ArrayList<>();
        Colorr = new ArrayList<>();
        Body = new ArrayList<>();
        Image = new ArrayList<>();


        displaystk();
        cus = new customAdapter(stkAfterType.this, ID, Title, Body, Timestamp, Colorr, Image);
        recyclerView.setAdapter(cus);
        recyclerView.setLayoutManager(new LinearLayoutManager(stkAfterType.this));

    }

    void displaystk() {
        Cursor cursor = mid.readstk();
        if (cursor.getCount() == 0) {

            Toast.makeText(this, "NO data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ID.add(cursor.getString(0));
                Title.add(cursor.getString(1));
                Body.add(cursor.getString(2));
                Timestamp.add(cursor.getString(3));
                Image.add(cursor.getString(4));
                Colorr.add(cursor.getString(5));

            }
        }
    }
}
package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.a4mation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class stkAfterType extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    DbHandler mid;
    ArrayList<String> ID, Title, Timestamp, Color, Body, Image;
    customAdapter cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_after_type);
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
        Color = new ArrayList<>();
        Body = new ArrayList<>();
        Image = new ArrayList<>();


        displaystk();
        cus = new customAdapter(stkAfterType.this, ID, Title, Body, Timestamp, Color, Image);
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
                Color.add(cursor.getString(5));

            }
        }
    }
}
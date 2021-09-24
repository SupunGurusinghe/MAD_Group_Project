package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a4mation.R;

public class stkAddItems extends AppCompatActivity {
ImageView additem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_add_items);
       additem = findViewById(R.id.img_notestkempty);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stkAddItems.this, stkTypeMsg.class));
            }
        });
    }
}
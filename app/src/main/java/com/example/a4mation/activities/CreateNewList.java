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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a4mation.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CreateNewList extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    EditText inputNoteTitle, inputNoteSubtitle, textDateTime1;
    ImageView imageSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_list);
        textDateTime1 = findViewById(R.id.textDateTime1);


        //update current date and time
        textDateTime1.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())

        );


        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertDate();
            }

        });


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
        getSupportActionBar().setTitle("List");
// getSupportActionBar().setSubtitle("Main");


// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.listItemStatusBar));
        }


        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Button
        Button add_items = findViewById(R.id.add_items);
        add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), ViewItemLists.class),
                        REQUEST_CODE_ADD_NOTE);
            }
        });


    }


    public void insertDate() {
        DbHandler myDB = new DbHandler(CreateNewList.this);
        boolean isInserted = myDB.createList(inputNoteTitle.getText().toString().trim(),
                inputNoteSubtitle.getText().toString().trim(),
                textDateTime1.getText().toString().trim());


        if (isInserted == true) {//Successful tost message
            Toast.makeText(CreateNewList.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
            startActivity(
                    new Intent(CreateNewList.this, ListView.class)
            );
        } else {//Unsuccessful tost message
            Toast.makeText(CreateNewList.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

        }

    }
}
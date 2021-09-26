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

public class LockReset extends AppCompatActivity {
    DbHandler myDb;

    private ImageView imageBack;
    private Button setKeyButton;
    private EditText addNewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_reset);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#DC143C"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        // Change Toolbar text
        getSupportActionBar().setTitle("Lock Password");
        // getSupportActionBar().setSubtitle("Main");

        // Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorStatusBar));
        }

        //Database creation
        myDb = new DbHandler(this);
        //Update
        addNewPassword = (EditText)findViewById(R.id.addNewPassword);

        //come back
        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LockReset.this, PasswordMain.class)
                );
            }
        });

        //reset key button
        setKeyButton = findViewById(R.id.setKeyButton);//take button id value
        setKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateSecurityKey(addNewPassword.getText().toString());//execute query
                if(isUpdated == true){ //Successful update toast
                    Toast.makeText(LockReset.this, "Successfully Inserted", Toast.LENGTH_LONG).show();
                    startActivity(
                            new Intent(LockReset.this, PasswordMain.class)
                    );
                }else{ //Unsuccessful update toast
                    Toast.makeText(LockReset.this, "Failed to change key", Toast.LENGTH_LONG).show();
                    startActivity(
                            new Intent(LockReset.this, PasswordMain.class)
                    );
                }
            }
        });
    }
}
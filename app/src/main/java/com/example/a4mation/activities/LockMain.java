package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import android.support.v4.app.*;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toolbar;


import com.example.a4mation.R;

public class LockMain extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_PASSWORD = 1;
    public static boolean isSet = true;
    private ImageView imageBack;
    private AlertDialog dialogSetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_main);

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

        ImageView addPasswordMain = findViewById(R.id.addPasswordMain);
        addPasswordMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSet){
                    startActivity(
                            new Intent(LockMain.this, LockOne.class)
                    );
                }else{
                    showSetPasswordDialog();
                }

            }
        });


        //move back
        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LockMain.this, Home.class)
                );
            }
        });
    }

    //set password dialog
    public void showSetPasswordDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LockMain.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_set_password,
                (ViewGroup) findViewById(R.id.layoutSetPasswordContainer)
        );
        builder.setView(view);
        dialogSetPassword = builder.create();
        if (dialogSetPassword.getWindow() != null) {
            dialogSetPassword.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }



        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetPassword.dismiss();
            }
        });
        dialogSetPassword.show();
    }
}
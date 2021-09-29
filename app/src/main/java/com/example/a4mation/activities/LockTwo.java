package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4mation.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LockTwo extends AppCompatActivity {
    // Database Object Declaration
    DbHandler myDb;



    private Button decryptButton;
    private EditText keyEdit;
    private TextView inputURL;
    TextView InputPasswordTitle2;
    TextView inputPassword2;
    TextView textDateTime2;
    TextView inputDescription2;
    private EditText answerQuestion;

    private LinearLayout layoutForgotPassword;

    private String passwordTitle, passwordPassword, passwordUrl, passwordDescription, passwordKey;
    private String title2, password2, description2, Url2, key2;


    //dialog popups
    private AlertDialog dialogIncorrectPassword;
    private AlertDialog dialogChangePassword;

    //color changing
    private String selectedNoteColor;
    private View viewPasswordIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_two);

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

        InputPasswordTitle2 = findViewById(R.id.InputPasswordTitle2);
        inputPassword2 = findViewById(R.id.inputPassword2);
        textDateTime2 = findViewById(R.id.textDateTime2);
        inputDescription2 = findViewById(R.id.inputDescription2);
        keyEdit = (EditText) findViewById(R.id.key);
        viewPasswordIndicator = findViewById(R.id.viewPasswordIndicator);

        //call intent
        getIntentData();


        //Decrypt password
        decryptButton = (Button) findViewById(R.id.decryptButton);
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyLock = myDb.getSecurityKey();
                if(keyLock.equals(keyEdit.getText().toString())){
                    String id = getIntent().getStringExtra("id");
                    String title = getIntent().getStringExtra("title");
                    String datetime = getIntent().getStringExtra("datetime");
                    String color = getIntent().getStringExtra("color");
                    String tempPassword = myDb.getPassword(id);
                    String tempDescription = myDb.getDescription(id);


                    Intent intent = new Intent(LockTwo.this, LockThree.class);
                    intent.putExtra("id2", id);
                    intent.putExtra("title2", title);
                    intent.putExtra("datetime2", datetime);
                    intent.putExtra("password2", tempPassword);
                    intent.putExtra("description2", tempDescription);
                    intent.putExtra("color2", color);
                    startActivity(intent);
                }else{
                    Toast.makeText(LockTwo.this, "Key is incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });




        inputURL = (TextView) findViewById(R.id.textWebUrl);

        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(LockTwo.this, PasswordMain.class)
                );
            }
        });

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //change password
        layoutForgotPassword = findViewById(R.id.layoutForgotPassword);
        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePasswordDialog();
            }
        });



    }


    //password incorrect dialog
    private void showPasswordIncorrectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockTwo.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_password_incorrect,
                (ViewGroup) findViewById(R.id.layoutIncorrectPasswordContainer)
        );
        builder.setView(view);
        dialogIncorrectPassword = builder.create();
        if (dialogIncorrectPassword.getWindow() != null) {
            dialogIncorrectPassword.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }



        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIncorrectPassword.dismiss();
            }
        });
        dialogIncorrectPassword.show();
    }



    //password change dialog
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockTwo.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_change_password,
                (ViewGroup) findViewById(R.id.layoutChangePasswordContainer)
        );
        builder.setView(view);
        dialogChangePassword = builder.create();
        if (dialogChangePassword.getWindow() != null) {
            dialogChangePassword.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        TextView displaySecurityQuestion = view.findViewById(R.id.displaySecurityQuestion);
        displaySecurityQuestion.setText(myDb.getSecurityQuestion());

        answerQuestion = view.findViewById(R.id.answer);

        String answer = myDb.getAnswer();
        view.findViewById(R.id.textConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answerQ = answerQuestion.getText().toString();
                if(answer.equals(answerQ)){
                    startActivity(
                            new Intent(LockTwo.this, LockReset.class)
                    );
                }else{
                    Toast.makeText(LockTwo.this, "Incorrect Answer "+ answerQ, Toast.LENGTH_LONG).show();
                }
            }
        });

        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChangePassword.dismiss();
            }
        });
        dialogChangePassword.show();
    }




    //data retrieve
    void getIntentData(){
        if(getIntent().hasExtra("id")){
            //get intent values
            String id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String datetime = getIntent().getStringExtra("datetime");
            String color = getIntent().getStringExtra("color");

            //call two functions
            String tempPassword = myDb.getPassword(id);
            String tempDescription = myDb.getDescription(id);


            //selecting condition
            if(color.equals("#333333")){
                selectedNoteColor = "#333333";
                setPasswordIndicatorColor();
            }else if(color.equals("#FDBE3B")){
                selectedNoteColor = "#FDBE3B";
                setPasswordIndicatorColor();
            }else if(color.equals("#FF4842")){
                selectedNoteColor = "#FF4842";
                setPasswordIndicatorColor();
            }else if(color.equals("#3A52Fc")){
                selectedNoteColor = "#3A52Fc";
                setPasswordIndicatorColor();
            }else if(color.equals("#000000")){
                selectedNoteColor = "#000000";
                setPasswordIndicatorColor();
            }


            //set values for views
            InputPasswordTitle2.setText(title);
            textDateTime2.setText(datetime);
            inputPassword2.setText(tempPassword);
            inputDescription2.setText(tempDescription);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    //Change color of password indicator
    private void setPasswordIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewPasswordIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }
}
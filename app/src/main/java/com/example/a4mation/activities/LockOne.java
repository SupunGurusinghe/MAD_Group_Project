package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LockOne extends AppCompatActivity {
    // Database Object Declaration
    DbHandler myDb;

    //Default Key
    public static String defaultKey = "abc";
    String temp = "";




    private ImageView imageBack;
    private ImageView imageNote;
    private Button encryptButton;
    private TextView textDateTime;
    private TextView textWebUrl;
    private LinearLayout layoutWebUrl;
    private LinearLayout layoutForgotPassword;
    private EditText titleEdit;
    private EditText passwordEdit;
    private EditText descriptionEdit;
    private Button mButton;
    private TextView inputURL;
    private EditText keyEdit;
    private String selectedImagePath;
    private String title, password, description, Url, key;
    private AlertDialog dialogAddUrl;
    private AlertDialog dialogIncorrectPassword;
    private AlertDialog dialogChangePassword;
    private EditText answerQuestion;
    private EditText textDateTime2;
    private String selectedNoteColor;
    private View viewPasswordIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_one);

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


        imageBack = findViewById(R.id.imageBack);
        encryptButton = findViewById(R.id.encryptButton);
        textDateTime = findViewById(R.id.textDateTime);
        textWebUrl = findViewById(R.id.textWebUrl);
        layoutWebUrl = findViewById(R.id.layoutWebUrl);

        //come back
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        //update current date and time
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())

        );

        //get inputs
        mButton = (Button) findViewById(R.id.encryptButton);
        titleEdit = (EditText) findViewById(R.id.InputPasswordTitle);
        passwordEdit = (EditText)findViewById(R.id.inputPassword);
        descriptionEdit = (EditText) findViewById(R.id.inputDescription);
        keyEdit = (EditText) findViewById(R.id.key);
        inputURL = (TextView) findViewById(R.id.textWebUrl);
        encryptButton = (Button)findViewById(R.id.encryptButton);
        textDateTime2 = (EditText) findViewById(R.id.textDateTime);


        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendData();
                    }
                }
        );



        // add url dialog layout popup
        LinearLayout layoutAddUrl = findViewById(R.id.layoutAddUrl);
        layoutAddUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUrlDialog();
            }
        });


        findViewById(R.id.imageRemoveWebUrl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textWebUrl.setText(null);
                layoutWebUrl.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.imageRemoveImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNote.setImageBitmap(null);
                imageNote.setVisibility(View.GONE);
                findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
                selectedImagePath = "";

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


        //default note color
        selectedNoteColor = "#333333";
        viewPasswordIndicator = findViewById(R.id.viewPasswordIndicator);

        ImageView imageColor1 = findViewById(R.id.imageColor1);
        ImageView imageColor2 = findViewById(R.id.imageColor2);
        ImageView imageColor3 = findViewById(R.id.imageColor3);
        ImageView imageColor4 = findViewById(R.id.imageColor4);
        ImageView imageColor5 = findViewById(R.id.imageColor5);

        View v1 = findViewById(R.id.viewColor1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#333333";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }
        });

        View v2 = findViewById(R.id.viewColor2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#FDBE3B";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }
        });

        View v3 = findViewById(R.id.viewColor3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#FF4842";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }
        });
        View v4 = findViewById(R.id.viewColor4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#3A52Fc";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }
        });
        View v5 = findViewById(R.id.viewColor5);
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#000000";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);
                setPasswordIndicatorColor();
            }
        });

    }






    private void showAddUrlDialog() {
        if (dialogAddUrl == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LockOne.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_add_url,
                    (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
            );
            builder.setView(view);

            dialogAddUrl = builder.create();
            if (dialogAddUrl.getWindow() != null) {
                dialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            final EditText inputUrl = view.findViewById(R.id.inputUrl);
            inputUrl.requestFocus();

            view.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputUrl.getText().toString().trim().isEmpty()) {
                        Toast.makeText(LockOne.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                        Toast.makeText(LockOne.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
                    } else {
                        textWebUrl.setText(inputUrl.getText().toString());
                        layoutWebUrl.setVisibility(View.VISIBLE);
                        dialogAddUrl.dismiss();
                    }
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUrl.dismiss();
                }
            });
        }
        dialogAddUrl.show();
    }





    //send Data method
    public void sendData(){
        title = titleEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();
        Url = inputURL.getText().toString().trim();
        description = descriptionEdit.getText().toString().trim();
        key = keyEdit.getText().toString().trim();
        String date = textDateTime2.getText().toString().trim();

        String Lockkey = myDb.getSecurityKey();
        if(key.equals(Lockkey)){

            char[] pwd = password.toCharArray();

            String temp = "";
            for(char c: pwd) {
                c += 5;
                temp = temp + c;
            }


            //Send to Database
            boolean isInserted = myDb.insertLockData(title, temp, description, date, selectedNoteColor);
            if(isInserted == true){
                Toast.makeText(LockOne.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                startActivity(
                        new Intent(LockOne.this, PasswordMain.class)
                );

            }else{
                Toast.makeText(LockOne.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
            }

        }else{
            showPasswordIncorrectDialog();
        }


    }


    //password incorrect dialog
    private void showPasswordIncorrectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockOne.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(LockOne.this);
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

        answerQuestion =  view.findViewById(R.id.answer);

        String answer = myDb.getAnswer();
        view.findViewById(R.id.textConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answerQ = answerQuestion.getText().toString();
                if(answer.equals(answerQ)){
                    startActivity(
                            new Intent(LockOne.this, LockReset.class)
                    );
                }else{
                    Toast.makeText(LockOne.this, "Incorrect Answer "+ answerQ, Toast.LENGTH_LONG).show();
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

    //Change color
    private void setPasswordIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewPasswordIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }
}


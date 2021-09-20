package com.example.a4mation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LockTwo extends AppCompatActivity {

    private Button decryptButton;
    private EditText keyEdit;
    private TextView titleEdit;
    private TextView passwordEdit;
    private TextView descriptionEdit;
    private TextView inputURL;

    private LinearLayout layoutForgotPassword;

    private TextView title, password, url, description;

    public static final String TITLE = "Title";
    public static final String PASSWORD = "Password";
    public static final String DESCRIPTION = "Description";
    public static final String URL = "WWW.GOOGLE.COM";

    private String passwordTitle, passwordPassword, passwordUrl, passwordDescription, passwordKey;
    private String title2, password2, description2, Url2, key2;

    private AlertDialog dialogIncorrectPassword;
    private AlertDialog dialogChangePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_two);

        title = findViewById(R.id.InputPasswordTitle);
        password = findViewById(R.id.inputPassword);
        url = findViewById(R.id.textWebUrl);
        description = findViewById(R.id.inputDescription);

        Intent i = getIntent();
        passwordTitle = i.getStringExtra(TITLE);
        passwordPassword = i.getStringExtra(PASSWORD);
        passwordUrl = i.getStringExtra(URL);
        passwordDescription = i.getStringExtra(DESCRIPTION);

        title.setText(passwordTitle);
        password.setText(passwordPassword);
        url.setText(passwordUrl);
        description.setText(passwordDescription);



        titleEdit = (TextView) findViewById(R.id.InputPasswordTitle);
        passwordEdit = (TextView)findViewById(R.id.inputPassword);
        descriptionEdit = (TextView) findViewById(R.id.inputDescription);
        keyEdit = (EditText) findViewById(R.id.key);
        inputURL = (TextView) findViewById(R.id.textWebUrl);

        decryptButton = findViewById(R.id.decryptButton);
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });


        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(LockTwo.this, LockMain.class)
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



        TextView textDateTime = findViewById(R.id.textDateTime);

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())

        );

        //change password
        layoutForgotPassword = findViewById(R.id.layoutForgotPassword);
        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePasswordDialog();
            }
        });
    }

    public void sendData(){
        title2 = titleEdit.getText().toString().trim();
        password2 = passwordEdit.getText().toString().trim();
        Url2 = inputURL.getText().toString().trim();
        description2 = descriptionEdit.getText().toString().trim();
        key2 = keyEdit.getText().toString().trim();

        if(key2.equals(LockOne.defaultKey)){

            char[] pwd = password2.toCharArray();

            String temp = "";
            for(char c: pwd) {
                c -= 5;
                temp = temp + c;
            }

            Intent i = new Intent(LockTwo.this, LockThree.class);

            i.putExtra(LockThree.TITLE2, title2);
            i.putExtra(LockThree.PASSWORD2, temp);
            i.putExtra(LockThree.URL2, Url2);
            i.putExtra(LockThree.DESCRIPTION2, description2);

            startActivity(i);
        }else{
            showPasswordIncorrectDialog();
        }


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



        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChangePassword.dismiss();
            }
        });
        dialogChangePassword.show();
    }
}
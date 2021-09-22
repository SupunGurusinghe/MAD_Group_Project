package com.example.a4mation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LockThree extends AppCompatActivity {

    private ImageView imageBack;
    private ImageView imageNote;
    private Button encryptButton;
    private TextView textDateTime;
    private TextView textWebUrl;
    private LinearLayout layoutWebUrl;
    private TextView title, password, url, description;

    private LinearLayout layoutForgotPassword;

    public static final String TITLE2 = "Title";
    public static final String PASSWORD2 = "Password";
    public static final String DESCRIPTION2 = "Description";
    public static final String URL2 = "WWW.GOOGLE.COM";

    private String passwordTitle, passwordPassword, passwordUrl, passwordDescription;
    private String selectedImagePath;

    private AlertDialog dialogDeleteNote;
    private AlertDialog dialogAddUrl;
    private AlertDialog dialogIncorrectPassword;
    private AlertDialog dialogChangePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_three);


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


        title = findViewById(R.id.InputPasswordTitle);
        password = findViewById(R.id.inputPassword);
        url = findViewById(R.id.textWebUrl);
        description = findViewById(R.id.inputDescription);

        Intent i = getIntent();
        passwordTitle = i.getStringExtra(TITLE2);
        passwordPassword = i.getStringExtra(PASSWORD2);
        passwordUrl = i.getStringExtra(URL2);
        passwordDescription = i.getStringExtra(DESCRIPTION2);

        title.setText(passwordTitle);
        password.setText(passwordPassword);
        url.setText(passwordUrl);
        description.setText(passwordDescription);



        //delete note dialog box popup
        ImageView imageDeleteNote = findViewById(R.id.imageDeleteNote);
        imageDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteNoteDialog();
            }
        });



        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button okButton = findViewById(R.id.okButton);
        EditText key = findViewById(R.id.key);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String tempKey = key.getText().toString().trim();
                if(tempKey.equals(LockOne.defaultKey)){
                    startActivity(
                            new Intent(LockThree.this, LockMain.class)
                    );
                }else{
                    showPasswordIncorrectDialog();
                }
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
    }


    private void showDeleteNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockThree.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_delete_note,
                (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer)
        );
        builder.setView(view);
        dialogDeleteNote = builder.create();
        if (dialogDeleteNote.getWindow() != null) {
            dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }



        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeleteNote.dismiss();
            }
        });
        dialogDeleteNote.show();
    }





    //add url
    private void showAddUrlDialog() {
        if (dialogAddUrl == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LockThree.this);
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
                        Toast.makeText(LockThree.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                        Toast.makeText(LockThree.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
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




    private void showPasswordIncorrectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockThree.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(LockThree.this);
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
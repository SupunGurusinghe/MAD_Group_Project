package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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

import com.example.a4mation.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LockThree extends AppCompatActivity {
    DbHandler myDb;
    Button okButton;

    private EditText InputPasswordTitle, inputPassword, inputDescription;
    private ImageView imageBack;
    private ImageView imageNote;
    private Button encryptButton;
    private TextView textDateTime;
    private TextView textWebUrl;
    private LinearLayout layoutWebUrl;

    private LinearLayout layoutForgotPassword;

    private String passwordTitle, passwordPassword, passwordUrl, passwordDescription;
    private String selectedImagePath;

    //Alert message boxes
    private AlertDialog dialogDeleteNote;
    private AlertDialog dialogAddUrl;
    private AlertDialog dialogIncorrectPassword;
    private AlertDialog dialogChangePassword;

    //color changing
    private String selectedNoteColor;
    private View viewPasswordIndicator;


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

        //Database creation
        myDb = new DbHandler(this);

        InputPasswordTitle = (EditText) findViewById(R.id.InputPasswordTitle);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputDescription = (EditText) findViewById(R.id.inputDescription);
        textDateTime = findViewById(R.id.textDateTime);

        //call the function to display
        getIntentData2();

        //Update part
        okButton = (Button)findViewById(R.id.okButton);


        //delete note dialog box popup
        ImageView imageDeleteNote = findViewById(R.id.imageDeleteNote);
        imageDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteNoteDialog();
            }
        });

        //update current date and time
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())

        );

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


        //Update
        EditText key = findViewById(R.id.key); //retrieve id value
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String temppwd = inputPassword.getText().toString();
                //Calculation
                char[] pwd = temppwd.toCharArray();
                String temp = "";
                for(char c: pwd) {
                    c += 5;
                    temp = temp + c;
                }
                //get intent id
                String id = getIntent().getStringExtra("id2");
                boolean isUpdate = myDb.updateLock(id, //execute database update function
                        InputPasswordTitle.getText().toString(),
                        temp,
                        selectedNoteColor,
                        inputDescription.getText().toString(),
                        textDateTime.getText().toString());

                if(isUpdate) {//successful update toast
                    Toast.makeText(LockThree.this, "Data Updated", Toast.LENGTH_LONG).show();
                    startActivity(
                            new Intent(LockThree.this, PasswordMain.class)
                    );
                }else{//unsuccessful update toast
                    Toast.makeText(LockThree.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //delete a note
    private void showDeleteNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockThree.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_delete_note, //delete note layout
                (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer) //id
        );
        builder.setView(view);
        dialogDeleteNote = builder.create();
        if (dialogDeleteNote.getWindow() != null) { //display dialog
            dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        //cancel dialog
        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeleteNote.dismiss();
            }
        });
        dialogDeleteNote.show();
        //when confirm clicks take id
        view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("id2");
                boolean isTrue = myDb.deleteOneLock(id); //database delete function execute
                if(isTrue){ //success message
                    Toast.makeText(LockThree.this, "Successfully Deleted!", Toast.LENGTH_LONG).show();
                    startActivity(
                            new Intent(LockThree.this, PasswordMain.class)
                    );
                }else{ //unsuccessful message
                    Toast.makeText(LockThree.this, "Delete Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    void getIntentData2(){
        if(getIntent().hasExtra("id2")){
            String id = getIntent().getStringExtra("id2");
            String title = getIntent().getStringExtra("title2");
            String datetime = getIntent().getStringExtra("datetime2");
            String color = getIntent().getStringExtra("color2");
            String tempPassword = myDb.getPassword(id);
            String tempDescription = myDb.getDescription(id);

            char[] pwd = tempPassword.toCharArray();
            String temp = "";
            for(char c: pwd) {
                c -= 5;
                temp = temp + c;
            }

            //change color
            viewPasswordIndicator = findViewById(R.id.viewPasswordIndicator);
            ImageView imageColor1 = findViewById(R.id.imageColor1);
            ImageView imageColor2 = findViewById(R.id.imageColor2);
            ImageView imageColor3 = findViewById(R.id.imageColor3);
            ImageView imageColor4 = findViewById(R.id.imageColor4);
            ImageView imageColor5 = findViewById(R.id.imageColor5);

            //selecting condition
            if(color.equals("#333333")){
                selectedNoteColor = "#333333";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }else if(color.equals("#FDBE3B")){
                selectedNoteColor = "#FDBE3B";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }else if(color.equals("#FF4842")){
                selectedNoteColor = "#FF4842";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }else if(color.equals("#3A52Fc")){
                selectedNoteColor = "#3A52Fc";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);
                setPasswordIndicatorColor();
            }else if(color.equals("#000000")){
                selectedNoteColor = "#000000";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);
                setPasswordIndicatorColor();
            }

            //set values for fields
            InputPasswordTitle.setText(title);
            inputPassword.setText(temp);
            inputDescription.setText(tempDescription);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    //Change color
    private void setPasswordIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewPasswordIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }

}
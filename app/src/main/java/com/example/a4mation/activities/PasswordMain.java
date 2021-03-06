package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a4mation.R;

import java.util.ArrayList;

public class PasswordMain extends AppCompatActivity {
    // Database Object Declaration
    DbHandler myDb;
    ArrayList<String> id, title, password, datetime, color, description;
    LockAdapter lockAdapter;
    RecyclerView recyclerView;

    public static final int REQUEST_CODE_ADD_PASSWORD = 1;

    private ImageView imageBack, search;

    //dialog boxes
    private AlertDialog dialogSetPassword;
    private AlertDialog dialogSetSecurityQuestion;

    //input field ids catching variables
    EditText inputSetKey, inputSecurityQuestion, inputSecurityQuestionAnswer, inputSearch;
    ImageView addPasswordMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_main);

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
        myDb = new DbHandler(PasswordMain.this);

        //catch recyclerview id
        recyclerView = findViewById(R.id.passwordRecyclerView);

        //elements of arraylist
        id = new ArrayList<>();
        title = new ArrayList<>();
        datetime = new ArrayList<>();
        color = new ArrayList<>();

        //call method for display lock data
        displayLocks();

        recyclerView.setLayoutManager(new LinearLayoutManager(PasswordMain.this));
        lockAdapter = new LockAdapter(PasswordMain.this, id, title, password, datetime, color, description);
        recyclerView.setAdapter(lockAdapter);

        addPasswordMain = findViewById(R.id.addPasswordMain);
        addPasswordMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = myDb.getSecurity();
                if(c.getCount() > 0){ //if data not exist
                    startActivity(
                            new Intent(PasswordMain.this, LockOne.class)
                    );
                }else{ //if data exist display add security question dialog
                    showSetPasswordDialog();
                }
            }
        });

        //come back
        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(PasswordMain.this, Home.class)
                );
            }
        });

        //search function
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockAdapter.getFilter().filter(inputSearch.getText().toString());
            }
        });
    }

    //call method for display lock data
    void displayLocks(){
        Cursor cursor = myDb.getLockData();
        if(cursor.getCount() == 0){
            Toast.makeText(PasswordMain.this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                datetime.add(cursor.getString(4));
                color.add(cursor.getString(5));
            }
        }
    }

    //set password dialog
    public void showSetPasswordDialog(){//Display alert
        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordMain.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_set_password, //layout name
                (ViewGroup) findViewById(R.id.layoutSetPasswordContainer) //layout id
        );
        builder.setView(view);
        dialogSetPassword = builder.create();
        if (dialogSetPassword.getWindow() != null) { //check null or not
            dialogSetPassword.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        //catch ids for variables
        inputSetKey = view.findViewById(R.id.inputSetKey);
        inputSecurityQuestion = view.findViewById(R.id.inputSecurityQuestion);
        inputSecurityQuestionAnswer = view.findViewById(R.id.inputSecurityQuestionAnswer);
        inputSecurityQuestionAnswer = view.findViewById(R.id.inputSecurityQuestionAnswer);
        //Onclick confirm
        view.findViewById(R.id.textConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Insert part
                boolean isInserted = myDb.insertSecurity(inputSecurityQuestion.getText().toString(),
                        inputSecurityQuestionAnswer.getText().toString(),
                        inputSetKey.getText().toString());//Insert function execute
                if(isInserted == true){//Successfully inserted toast
                    Toast.makeText(PasswordMain.this, "Security Question Inserted", Toast.LENGTH_LONG).show();
                    startActivity(
                            new Intent(PasswordMain.this, LockOne.class)
                    );
                }else{//Unsuccessful toast
                    Toast.makeText(PasswordMain.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Cancel button
        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetPassword.dismiss();
            }
        });
        dialogSetPassword.show();
    }

}
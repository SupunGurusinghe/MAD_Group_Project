package com.example.a4mation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.a4mation.R;

public class EditTodo extends AppCompatActivity {

    private EditText title, desc;
    private Button edit;
    private DbHandler dbHandler;
    private Context context;
    private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);


        context = this;
        dbHandler = new DbHandler(context);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();



// Define ColorDrawable object and parse color
// using parseColor method
// with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#2196F3"));





// Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);



// Change Toolbar text
        getSupportActionBar().setTitle("TODO");
// getSupportActionBar().setSubtitle("Main");



// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorTo));
        }


        title = findViewById(R.id.editToDOTextTitle);
        desc = findViewById(R.id.editToDOTextDescription);
        edit = findViewById(R.id.buttonEdit);

        final String id = getIntent().getStringExtra("id");
        ToDo todo = dbHandler.getSingeleToDo(Integer.parseInt(id));

        title.setText(todo.getTitle());
        desc.setText(todo.getDescription());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = title.getText().toString();
                String descText = desc.getText().toString();
                updateDate = System.currentTimeMillis();


                ToDo toDo = new ToDo(Integer.parseInt(id),titleText,descText,updateDate,0);
                int state = dbHandler.updateSingelToDo(toDo);
                startActivity(new Intent(context, TodoHome.class));
            }
        });

    }
}
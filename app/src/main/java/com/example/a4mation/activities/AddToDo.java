package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a4mation.R;

public class AddToDo extends AppCompatActivity {

    private EditText title, desc;
    private Button add, gotoedit;
    private DbHandler dbHandler;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        title = findViewById(R.id.editToDOTextTitle);
        desc = findViewById(R.id.editToDOTextDescription);
        add = findViewById(R.id.buttonEdit);
        gotoedit = findViewById(R.id.gotoeditbutton);

        context = this;
        dbHandler = new DbHandler(context);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTitle = title.getText().toString();
                String userDesc = desc.getText().toString();
                long started = System.currentTimeMillis();

                ToDo toDo = new ToDo(userTitle,userDesc,started, 0);
                dbHandler.addToDo(toDo);

                startActivity(new Intent(context, TodoHome.class));

            }
        });

        gotoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, EditTodo.class));
            }
        });
    }
}
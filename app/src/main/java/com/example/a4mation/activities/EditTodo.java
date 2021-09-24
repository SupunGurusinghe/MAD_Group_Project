package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.a4mation.R;

public class EditTodo extends AppCompatActivity {

    private EditText title, desc;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        title = findViewById(R.id.editToDOTextTitle);
        desc = findViewById(R.id.editToDOTextDescription);
        edit = findViewById(R.id.buttonEdit);
    }
}
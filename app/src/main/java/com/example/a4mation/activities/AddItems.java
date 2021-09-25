package com.example.a4mation.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a4mation.R;

public class AddItems extends AppCompatActivity {

    EditText item_input,quentity_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        item_input = findViewById(R.id.item_input);
        quentity_input = findViewById(R.id.quentity_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            DbHandler myDB = new DbHandler(AddItems.this);
            myDB.addItems(item_input.getText().toString().trim(),
                    Integer.valueOf(quentity_input.getText().toString().trim()));

        });

    }
}
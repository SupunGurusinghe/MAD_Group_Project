package com.example.a4mation.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a4mation.R;

public class ListItemsUpdate extends AppCompatActivity {

    EditText item_input, quentity_input;
    Button update_button, delete_button;

    String id, item, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items_update);

        item_input = findViewById(R.id.item_input2);
        quentity_input = findViewById(R.id.quentity_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First call this
        getAndSetIntentData();

        //set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(item);
        }

        update_button.setOnClickListener(view -> {
            // Now Call this
            DbHandler myDB = new DbHandler(ListItemsUpdate.this);
            item = item_input.getText().toString().trim();
            quantity = quentity_input.getText().toString().trim();
            myDB.updateData(id, item, quantity);

        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("item") &&
                getIntent().hasExtra("quantity")){
            // Getting Data from Intent
            id = getIntent().getStringExtra("id");
            item = getIntent().getStringExtra("item");
            quantity = getIntent().getStringExtra("quantity");

            // Setting Intent Data
            item_input.setText(item);
            quentity_input.setText(quantity);

        }else{
            Toast.makeText(this,"No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + item + " ?");
        builder.setMessage("Are you sure you want to delete " + item + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DbHandler myDB = new DbHandler(ListItemsUpdate.this);
                myDB.deleteOneRow(id);
                //finish();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

}
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a4mation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class stkAfterType extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    DbHandler mid;
    ArrayList<String> ID, Title, Timestamp, Colorr, Body, Image;
    ArrayList<String> ii, tl, tm, col;
    customAdapter cus;
    EditText searchstk;
    private AlertDialog dialogDeletestkNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_after_type);
        searchstk = findViewById(R.id.searchstkinput);

        searchstk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();



// Define ColorDrawable object and parse color
// using parseColor method
// with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#669900"));





// Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);



// Change Toolbar text
        getSupportActionBar().setTitle("All Sticky Notes");
// getSupportActionBar().setSubtitle("Main");



// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorNew));
        }

        
        recyclerView = findViewById(R.id.stkRecyclerView);
        fab = findViewById(R.id.bt_float);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stkAfterType.this, stkTypeMsg.class));
            }
        });
        mid = new DbHandler(stkAfterType.this);
        ID = new ArrayList<>();
        Title = new ArrayList<>();
        Timestamp = new ArrayList<>();
        Colorr = new ArrayList<>();
        Body = new ArrayList<>();
        Image = new ArrayList<>();



         //call function within oncreate() to display data as recyclerview
        displaystk();
        cus = new customAdapter(stkAfterType.this, ID, Title, Body, Timestamp, Colorr, Image);
        recyclerView.setAdapter(cus);
        recyclerView.setLayoutManager(new LinearLayoutManager(stkAfterType.this));
    }

   //display retrieved all sticky notes data
    void displaystk() {
        Cursor cursor = mid.readstk();
        if (cursor.getCount() == 0) {
        //display message if there are no data to display
                Toast.makeText(this, "NO data", Toast.LENGTH_SHORT).show();
        } else {
        //otherwise display all data records
            while (cursor.moveToNext()) {
                ID.add(cursor.getString(0));
                Title.add(cursor.getString(1));
                Body.add(cursor.getString(2));
                Timestamp.add(cursor.getString(3));
                Image.add(cursor.getString(4));
                Colorr.add(cursor.getString(5));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stk_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

     //delete all data at once if user want (by displaying popup)
    private void showDeletestkNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(stkAfterType.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_deleteall_stk,
                (ViewGroup) findViewById(R.id.stk_dltall_layout)
        );
        builder.setView(view);
        dialogDeletestkNote = builder.create();
        if (dialogDeletestkNote.getWindow() != null) {
            dialogDeletestkNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        view.findViewById(R.id.stkDeleteallConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler myDelete = new DbHandler(stkAfterType.this);

                myDelete.deleteAllStk();
                startActivity(new Intent(stkAfterType.this, stkAfterType.class));
            }
        });
        view.findViewById(R.id.stkallcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeletestkNote.dismiss();
            }
        });
        dialogDeletestkNote.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.deleteallStk){

            showDeletestkNoteDialog();

        }
        return super.onOptionsItemSelected(item);
    }
}
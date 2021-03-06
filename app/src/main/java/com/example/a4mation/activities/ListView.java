package com.example.a4mation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a4mation.R;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    RecyclerView notesRecyclerView;
    ImageView imageAddNoteMain;

    DbHandler myDB;
    ArrayList<String> list_id, list_title, list_subtitle, list_date;
    MyCustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ListView.this, CreateNewList.class);
                startActivity(intent);
            }
        });


        myDB = new DbHandler(ListView.this);
        list_id = new ArrayList<>();
        list_title = new ArrayList<>();
        list_subtitle = new ArrayList<>();
        list_date = new ArrayList<>();



        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();



// Define ColorDrawable object and parse color
// using parseColor method
// with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF6347"));



// Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);



// Change Toolbar text
        getSupportActionBar().setTitle("List");
// getSupportActionBar().setSubtitle("Main");



// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.listItemStatusBar));
        }

        storeListData();

        customAdapter = new MyCustomAdapter(ListView.this, list_id, list_title, list_subtitle, list_date);
        notesRecyclerView.setAdapter(customAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(ListView.this));

    }

    void storeListData(){
        Cursor cursor = myDB.readData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                list_id.add(cursor.getString(0));
                list_title.add(cursor.getString(1));
                list_subtitle.add(cursor.getString(2));
                list_date.add(cursor.getString(3));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_list_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all_list){

            confirmDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DbHandler myDB = new DbHandler(ListView.this);
                myDB.deleteAllListData();
                // Refresh Activity
                Intent intent = new Intent(ListView.this,ListView.class);
                startActivity(intent);
                finish();
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
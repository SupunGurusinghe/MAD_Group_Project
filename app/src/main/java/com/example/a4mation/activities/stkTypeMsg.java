package com.example.a4mation.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.provider.MediaStore;

import com.example.a4mation.R;

import java.io.InputStream;

public class stkTypeMsg extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private TextView textWebUrl, tv_count, tv_wcount;
    private LinearLayout webLinearLayout;
    private String selectedImagePath;
    private AlertDialog dialogAddUrl;
    private ImageView imgStk;
    private String stkColor;
    private View viewstkIndicator;
    private ImageView imgOk;
    private EditText etitle ,ebody ,eurl;
    private String title, body, url, timestamp;
    private Uri selectedImageUri;
    private View viewcolor;
    private SeekBar sBar;
    int seekValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_type_msg);

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
        getSupportActionBar().setTitle("");
// getSupportActionBar().setSubtitle("Main");



// Change the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorNew));
        }


        viewstkIndicator = findViewById(R.id.viewstkIndicator);
        textWebUrl = findViewById(R.id.weburl);
        webLinearLayout = findViewById(R.id.ll_web);
        imgOk = findViewById(R.id.img_ok);
        etitle = findViewById(R.id.et_tl);
        ebody = findViewById(R.id.et_bd);
        eurl = findViewById(R.id.et_inputurl);
        selectedImagePath = "";
        tv_count = findViewById(R.id.tv_ccount);
        tv_wcount = findViewById(R.id.tv_wcount);

        //catch seekbar from id
        sBar = findViewById(R.id.sBar);
        //change the font size of sticky note by using seek bar
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekValue= i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ebody.setText(ebody.getText().toString());//change font size after stopped touch
                ebody.setTextSize(seekValue);
            }
        });



        ebody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int character = ebody.length();
                int ch1 = etitle.length();
                int total = character + ch1;
                String convert = String.valueOf(total);
                tv_count.setText(convert);
                int totalWord = wordcount(ebody.getText().toString()) + wordcount(etitle.getText().toString());
                String v = String.valueOf(totalWord);
                tv_wcount.setText(v);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



         //onclick for data insertion
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler mdb = new DbHandler(stkTypeMsg.this);
                //call function for data insertion
                mdb.insertData(etitle.getText().toString().trim(), ebody.getText().toString().trim(),selectedImagePath, stkColor);
            }
        });



        stkColor = "#333333";//default color
        viewstkIndicatorcolor();
        ImageView imgCol2 = findViewById(R.id.imgCol2);
        ImageView imgCol1 = findViewById(R.id.imgCol1);
        ImageView imgCol3 = findViewById(R.id.imgCol3);
        ImageView imgCol4 = findViewById(R.id.imgCol4);
        ImageView imgCol5 = findViewById(R.id.imgCol5);
        View v1 = findViewById(R.id.viewCol1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stkColor = "#333333";//default selected color
                imgCol1.setImageResource(R.drawable.ic_done);
                imgCol2.setImageResource(0);
                imgCol3.setImageResource(0);
                imgCol4.setImageResource(0);
                imgCol5.setImageResource(0);
                viewstkIndicatorcolor();
            }
        });
        // if yellow color round selected
        View v2 = findViewById(R.id.viewCol2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stkColor = "#FDBE3B";
                imgCol1.setImageResource(0);
                imgCol2.setImageResource(R.drawable.ic_done);
                imgCol3.setImageResource(0);
                imgCol4.setImageResource(0);
                imgCol5.setImageResource(0);
                viewstkIndicatorcolor();
            }
        });
        // if red color round selected
        View v3 = findViewById(R.id.viewCol3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stkColor = "#FF4842";
                imgCol1.setImageResource(0);
                imgCol2.setImageResource(0);
                imgCol3.setImageResource(R.drawable.ic_done);
                imgCol4.setImageResource(0);
                imgCol5.setImageResource(0);
                viewstkIndicatorcolor();
            }
        });
        // if blue color round selected
        View v4 = findViewById(R.id.viewCol4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stkColor = "#3A52Fc";
                imgCol1.setImageResource(0);
                imgCol2.setImageResource(0);
                imgCol3.setImageResource(0);
                imgCol4.setImageResource(R.drawable.ic_done);
                imgCol5.setImageResource(0);
                viewstkIndicatorcolor();
            }
        });
        // if black color round selected
        View v5 = findViewById(R.id.viewCol5);
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stkColor = "#000000";
                imgCol1.setImageResource(0);
                imgCol2.setImageResource(0);
                imgCol3.setImageResource(0);
                imgCol4.setImageResource(0);
                imgCol5.setImageResource(R.drawable.ic_done);
                viewstkIndicatorcolor();
            }
        });



        // adding url (popup)
        LinearLayout link = findViewById(R.id.uadding);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUrlDialogbox();
            }
        });


        //catch image adding layout
        LinearLayout imageAdd = findViewById(R.id.pAdding);
        imgStk = findViewById(R.id.imgStk);
        //if clicked to add image for sticky note
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //asking permission to access external storage
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(stkTypeMsg.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {//if permission get
                    selectImage();
                }
            }
        });
    }


    public static int wordcount(String line) {
        int numWords = 0;
        int index = 0;
        boolean prevWhiteSpace = true;
        while (index < line.length()) {
            char c = line.charAt(index++);
            boolean currWhiteSpace = Character.isWhitespace(c);
            if (prevWhiteSpace && !currWhiteSpace) {
                numWords++;
            }
            prevWhiteSpace = currWhiteSpace;
        }
        return numWords;
    }


    //If permission granted then add image
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputstream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                        imgStk.setImageBitmap(bitmap);
                        imgStk.setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFormUri(selectedImageUri);

                    } catch (Exception exception) {
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }


    //getting image path (for insert image to database)
    private String getPathFormUri(Uri contenturi){

        String filepath;
        Cursor cursor = getContentResolver().query(contenturi, null, null, null, null);
        if(cursor == null) {
            filepath = contenturi.getPath();

        }else{
                cursor.moveToFirst();
                int index = cursor.getColumnIndex("_data");
                filepath = cursor.getString(index);
                cursor.close();

            }
            return filepath;
        }


     //adding URL (popup dialog)
    private void showAddUrlDialogbox() {
        if (dialogAddUrl == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(stkTypeMsg.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.stk_link_popup,//give layout name for popup
                    (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
            );
            builder.setView(view);
            dialogAddUrl = builder.create();
            if (dialogAddUrl.getWindow() != null) {
                dialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            final EditText inputUrl = view.findViewById(R.id.et_inputurl);//catch the url
            inputUrl.requestFocus();
            //if clicked confirm button
            view.findViewById(R.id.et_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //check whether URL is empty
                    if (inputUrl.getText().toString().trim().isEmpty()) {
                     //display error message to enter an url
                        Toast.makeText(stkTypeMsg.this, "Enter URL", Toast.LENGTH_SHORT).show();
                     //check whether URL is a valid URL
                    } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                        //display error message to enter valid url
                        Toast.makeText(stkTypeMsg.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
                    } else {
                        //otherwise it assign to a text view
                        textWebUrl.setText(inputUrl.getText().toString());
                        webLinearLayout.setVisibility(View.VISIBLE);
                        dialogAddUrl.dismiss();//popup disappear
                    }
                }
            });//if user wants to cancel adding URL
            view.findViewById(R.id.et_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUrl.dismiss();
                }//popup disappear
            });
        }
        dialogAddUrl.show();
    }


    private void viewstkIndicatorcolor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewstkIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(stkColor));
    }


/*
    public void AddData(){
        imgOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean inserted = dbHelper.insertData(etitle.getText().toString(),ebody.getText().toString());
                        if(inserted = true){
                            Toast.makeText(stkTypeMsg.this, "saved", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(stkTypeMsg.this, "unsuccessfull", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }*/
}
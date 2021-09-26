package com.example.a4mation.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.a4mation.R;

import java.io.InputStream;

public class stkDisplayEdit extends AppCompatActivity {

    private String estkColor;
    private View eviewstkIndicator;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private TextView etextWebUrl,tv_ccount, tv_wwcount;;
    private LinearLayout ewebLinearLayout;
    private String eselectedImagePath;
    private AlertDialog edialogAddUrl;
    private ImageView eimgStk;
    private ImageView update, delete,homeimg;
    private EditText ID, Title, Timestamp, Body;
    private ImageView imageset;
    private String id,title,body, timestamp, color, image;
    private AlertDialog dialogDeletestkNote;
    int eseekValue;
    private SeekBar esBar;
    private static final String SHARED_PRESS = "prefs";







    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_display_edit);


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
        etextWebUrl = findViewById(R.id.eweburl);
        ewebLinearLayout = findViewById(R.id.ell_web);
        Title = findViewById(R.id.eet_tl);
        Body = findViewById(R.id.eet_bd);
        imageset = findViewById(R.id.eimgStk);
        update = findViewById(R.id.img_o);
        delete = findViewById(R.id.stkdelete);
        eselectedImagePath = "";
        esBar = findViewById(R.id.esBar);
        tv_ccount = findViewById(R.id.tv_ccount);
        tv_wwcount = findViewById(R.id.tv_wwcount);
        homeimg = findViewById(R.id.homeimg);
        //init paper

        homeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        esBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                eseekValue= i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Body.setText(Body.getText().toString());
                Body.setTextSize(eseekValue);
            }
        });

        //calling function to get data through intent
        getstkintentdata();
        //onclick for data updating
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler dbm = new DbHandler(stkDisplayEdit.this);
                //call update function
                dbm.updatestk(id, Title.getText().toString().trim(), Body.getText().toString().trim(), estkColor, eselectedImagePath);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDeletestkNoteDialog();
                  }
        });

       //function to calculate character count of edit texts (note title & note body)
        Body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int character = Body.length();//getting length of edit text(body)
                int ch1 = Title.length();//getting length of edit text(title)
                int total = character + ch1;//get total
                String convert = String.valueOf(total);//convert total into sting value
                tv_ccount.setText(convert);//assign string value into text view
                int totalWord = wordcount(Body.getText().toString()) + wordcount(Title.getText().toString());
                      //call word count function and get total(body & title)
                String v = String.valueOf(totalWord);//convert into string value
                tv_wwcount.setText(v);//assign string value into text view
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int character = Body.length();//done same thing when text change
                int ch1 = Title.length();
                int total = character + ch1;
                String convert = String.valueOf(total);
                tv_ccount.setText(convert);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        eviewstkIndicator = findViewById(R.id.eviewstkIndicator);
    estkColor = "#333333";
    eviewstkIndicatorcolor();
    ImageView eimgCol2 = findViewById(R.id.eimgCol2);
    ImageView eimgCol1 = findViewById(R.id.eimgCol1);
    ImageView eimgCol3 = findViewById(R.id.eimgCol3);
    ImageView eimgCol4 = findViewById(R.id.eimgCol4);
    ImageView eimgCol5 = findViewById(R.id.eimgCol5);

    View v1 = findViewById(R.id.eviewCol1);
        v1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            estkColor = "#333333";
            eimgCol1.setImageResource(R.drawable.ic_done);
            eimgCol2.setImageResource(0);
            eimgCol3.setImageResource(0);
            eimgCol4.setImageResource(0);
            eimgCol5.setImageResource(0);
            eviewstkIndicatorcolor();
        }
    });

    View v2 = findViewById(R.id.eviewCol2);
        v2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            estkColor = "#FDBE3B";
            eimgCol1.setImageResource(0);
            eimgCol2.setImageResource(R.drawable.ic_done);
            eimgCol3.setImageResource(0);
            eimgCol4.setImageResource(0);
            eimgCol5.setImageResource(0);
            eviewstkIndicatorcolor();
        }
    });

    View v3 = findViewById(R.id.eviewCol3);
        v3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            estkColor = "#FF4842";
            eimgCol1.setImageResource(0);
            eimgCol2.setImageResource(0);
            eimgCol3.setImageResource(R.drawable.ic_done);
            eimgCol4.setImageResource(0);
            eimgCol5.setImageResource(0);
            eviewstkIndicatorcolor();
        }
    });
    View v4 = findViewById(R.id.eviewCol4);
        v4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            estkColor = "#3A52Fc";
            eimgCol1.setImageResource(0);
            eimgCol2.setImageResource(0);
            eimgCol3.setImageResource(0);
            eimgCol4.setImageResource(R.drawable.ic_done);
            eimgCol5.setImageResource(0);
            eviewstkIndicatorcolor();
        }
    });
    View v5 = findViewById(R.id.eviewCol5);
        v5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            estkColor = "#000000";
            eimgCol1.setImageResource(0);
            eimgCol2.setImageResource(0);
            eimgCol3.setImageResource(0);
            eimgCol4.setImageResource(0);
            eimgCol5.setImageResource(R.drawable.ic_done);
            eviewstkIndicatorcolor();
        }
    });

        // add url dialog layout popup
        LinearLayout link = findViewById(R.id.euadding);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUrlDialogbox();
            }
        });
        LinearLayout imageAdd = findViewById(R.id.epAdding);
        eimgStk = findViewById(R.id.eimgStk);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(stkDisplayEdit.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    selectImage();
                }
            }
        });
    }


//function to calculate word count of edit texts (note title & note body)
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



         //get earlier inserted data that to be updated through intent
    void getstkintentdata(){
        if (getIntent().hasExtra("ID") && getIntent().hasExtra("Title") && getIntent().hasExtra("Timestamp")) {
                  //getting data from intent and assign to strings
            id = getIntent().getStringExtra("ID");
            title = getIntent().getStringExtra("Title");
            timestamp = getIntent().getStringExtra("Timestamp");
            body = getIntent().getStringExtra("Body");
            estkColor = getIntent().getStringExtra("Color");
            image = getIntent().getStringExtra("Image");

                 //setting intent data to edit texts
            Title.setText(title);
            Body.setText(body);
        } else {
            //display no data message if there are no data
            Toast.makeText(this, "Nodata",Toast.LENGTH_SHORT).show();
        }
    }



   //delete selected sticky note (using popup)
    private void showDeletestkNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(stkDisplayEdit.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_delete_stk,
                (ViewGroup) findViewById(R.id.stk_dlt_layout)
        );
        builder.setView(view);
        dialogDeletestkNote = builder.create();
        if (dialogDeletestkNote.getWindow() != null) {
            dialogDeletestkNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        view.findViewById(R.id.stkDeleteConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler dbd = new DbHandler(stkDisplayEdit.this);
                dbd.deleteonestk(id);
            }
        });
        view.findViewById(R.id.stkcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeletestkNote.dismiss();
            }
        });
        dialogDeletestkNote.show();
    }




    private void eviewstkIndicatorcolor(){
        GradientDrawable gradientDrawable = (GradientDrawable) eviewstkIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(estkColor));
    }


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
        Uri eselectedImageUri = data.getData();
        if(eselectedImageUri != null){

        try{
        InputStream inputstream = getContentResolver().openInputStream(eselectedImageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
        eimgStk.setImageBitmap(bitmap);
        eimgStk.setVisibility(View.VISIBLE);

            eselectedImagePath = getPathFormUri(eselectedImageUri);

        } catch (Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }
        }
        }

        }
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



private void showAddUrlDialogbox() {
        if (edialogAddUrl == null) {
        AlertDialog.Builder builder = new AlertDialog.Builder(stkDisplayEdit.this);
        View view = LayoutInflater.from(this).inflate(
        R.layout.stk_link_popup,
        (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
        );
        builder.setView(view);

        edialogAddUrl = builder.create();
        if (edialogAddUrl.getWindow() != null) {
        edialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
final EditText inputUrl = view.findViewById(R.id.et_inputurl);
        inputUrl.requestFocus();

        view.findViewById(R.id.et_confirm).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        if (inputUrl.getText().toString().trim().isEmpty()) {
        Toast.makeText(stkDisplayEdit.this, "Enter URL", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
        Toast.makeText(stkDisplayEdit.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
        } else {
        etextWebUrl.setText(inputUrl.getText().toString());
        ewebLinearLayout.setVisibility(View.VISIBLE);
        edialogAddUrl.dismiss();
        }
        }
        });

        view.findViewById(R.id.et_cancel).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        edialogAddUrl.dismiss();
        }
        });
        }
        edialogAddUrl.show();
        }

}
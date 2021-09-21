package com.example.a4mation.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.provider.MediaStore;

import com.example.a4mation.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class stkTypeMsg extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private TextView textWebUrl;
    private LinearLayout webLinearLayout;
    private String selectedImagePath;
    private AlertDialog dialogAddUrl;
    private ImageView imgStk;
    private String stkColor;
    private View viewstkIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_type_msg);
        viewstkIndicator = findViewById(R.id.viewstkIndicator);
        textWebUrl = findViewById(R.id.weburl);
        webLinearLayout = findViewById(R.id.ll_web);


        stkColor = "#333333";
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
                stkColor = "#333333";
                imgCol1.setImageResource(R.drawable.ic_done);
                imgCol2.setImageResource(0);
                imgCol3.setImageResource(0);
                imgCol4.setImageResource(0);
                imgCol5.setImageResource(0);
                viewstkIndicatorcolor();
            }
        });

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

        // add url dialog layout popup
        LinearLayout link = findViewById(R.id.uadding);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUrlDialogbox();
            }
        });
        LinearLayout imageAdd = findViewById(R.id.pAdding);
        imgStk = findViewById(R.id.imgStk);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(stkTypeMsg.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    selectImage();
                }

            }
        });


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
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){

                    try{
                        InputStream inputstream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                        imgStk.setImageBitmap(bitmap);
                        imgStk.setVisibility(View.VISIBLE);

                    } catch (Exception exception) {
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }



    private void showAddUrlDialogbox() {
        if (dialogAddUrl == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(stkTypeMsg.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.stk_link_popup,
                    (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
            );
            builder.setView(view);

            dialogAddUrl = builder.create();
            if (dialogAddUrl.getWindow() != null) {
                dialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            final EditText inputUrl = view.findViewById(R.id.et_inputurl);
            inputUrl.requestFocus();

            view.findViewById(R.id.et_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputUrl.getText().toString().trim().isEmpty()) {
                        Toast.makeText(stkTypeMsg.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                        Toast.makeText(stkTypeMsg.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
                    } else {
                        textWebUrl.setText(inputUrl.getText().toString());
                        webLinearLayout.setVisibility(View.VISIBLE);
                        dialogAddUrl.dismiss();
                    }
                }
            });

            view.findViewById(R.id.et_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUrl.dismiss();
                }
            });
        }
        dialogAddUrl.show();
    }


    private void viewstkIndicatorcolor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewstkIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(stkColor));
    }
}
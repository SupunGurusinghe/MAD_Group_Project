package com.example.a4mation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a4mation.R;

public class stkDisplayEdit extends AppCompatActivity {

    private String estkColor;
    private View eviewstkIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk_display_edit);


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

}


    private void eviewstkIndicatorcolor(){
        GradientDrawable gradientDrawable = (GradientDrawable) eviewstkIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(estkColor));
    }
}

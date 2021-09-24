package com.example.a4mation.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4mation.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.MyViewHolder> {


    private Context context;
    private ArrayList ID, Title, Body, Timestamp, Colorstk, Image;



    customAdapter(Context context,
                  ArrayList ID,
                  ArrayList Title, ArrayList Body, ArrayList Timestamp, ArrayList Colorstk, ArrayList Image){
        this.context = context;
        this.ID = ID;
        this.Title = Title;
        this.Body = Body;
        this.Timestamp = Timestamp;
        this.Colorstk = Colorstk;
        this.Image = Image;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID_txt, Title_txt, Timestamp_txt;

        LinearLayout stklayout;
        ImageView Image_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID_txt = itemView.findViewById(R.id.ID_txtt);
            Title_txt = itemView.findViewById(R.id.Title_txtt);
            Timestamp_txt = itemView.findViewById(R.id.Timestamp_txtt);
            stklayout = itemView.findViewById(R.id.layoutstkcontainer);


        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stk_container,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.ID_txt.setText(String.valueOf(ID.get(position)));
            holder.Title_txt.setText(String.valueOf(Title.get(position)));
            holder.Timestamp_txt.setText(String.valueOf(Timestamp.get(position)));
            holder.stklayout.setBackgroundColor(Color.parseColor(String.valueOf(Colorstk.get(position))));
            holder.stklayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, stkDisplayEdit.class);


                    intent.putExtra("ID", String.valueOf(ID.get(holder.getAdapterPosition())));
                    intent.putExtra("Title", String.valueOf(Title.get(holder.getAdapterPosition())));
                    intent.putExtra("Timestamp", String.valueOf(Timestamp.get(holder.getAdapterPosition())));
                    intent.putExtra("Body", String.valueOf(Body.get(holder.getAdapterPosition())));
                    intent.putExtra("Color", String.valueOf(Colorstk.get(holder.getAdapterPosition())));







                    context.startActivity(intent);


                }
            });
    }

    @Override
    public int getItemCount() {

        return ID.size();
    }


}




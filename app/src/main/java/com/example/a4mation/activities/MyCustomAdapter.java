package com.example.a4mation.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4mation.R;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

       private Context context;
       private ArrayList list_id, list_title,list_subtitle;

       MyCustomAdapter(Context context, ArrayList list_id, ArrayList list_title, ArrayList list_subtitle){

           this.context = context;
           this.list_id = list_id;
           this.list_title = list_title;
           this.list_subtitle = list_subtitle;
       }



    @NonNull
    @Override
    public MyCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row_create,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           holder.list_title_txt.setText(String.valueOf(list_title.get(position)));
           holder.list_subtitle_txt.setText(String.valueOf(list_subtitle.get(position)));

    }

    @Override
    public int getItemCount() {
        return list_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

           TextView list_title_txt, list_subtitle_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            list_title_txt = itemView.findViewById(R.id.list_title_txt);
            list_subtitle_txt = itemView.findViewById(R.id.list_subtitle_txt);

        }
    }
}

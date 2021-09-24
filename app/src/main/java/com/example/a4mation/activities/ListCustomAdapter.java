package com.example.a4mation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4mation.R;

import java.util.ArrayList;

public class ListCustomAdapter extends RecyclerView.Adapter<ListCustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList item_id, item_name,item_quentity;

    Animation list_translate_anim;



    ListCustomAdapter(Activity activity, Context context, ArrayList item_id, ArrayList item_name, ArrayList item_quentity){
        this.activity = activity;
        this.context = context;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_quentity = item_quentity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.item_id_txt.setText(String.valueOf(item_id.get(position)));
        holder.item_name_txt.setText(String.valueOf(item_name.get(position)));
        holder.item_quentity_txt.setText(String.valueOf(item_quentity.get(position)));
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ListItemsUpdate.class);
            intent.putExtra("id", String.valueOf(item_id.get(position)));
            intent.putExtra("item", String.valueOf(item_name.get(position)));
            intent.putExtra("quantity", String.valueOf(item_quentity.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item_id_txt, item_name_txt, item_quentity_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id_txt = itemView.findViewById(R.id.item_id_txt);
            item_name_txt = itemView.findViewById(R.id.item_name_txt);
            item_quentity_txt = itemView.findViewById(R.id.item_quentity_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            // Animate RecycleView
            list_translate_anim = AnimationUtils.loadAnimation(context, R.anim.list_translate_anim);
            mainLayout.setAnimation(list_translate_anim);
        }
    }
}

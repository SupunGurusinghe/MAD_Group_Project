package com.example.a4mation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4mation.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class LockAdapter extends RecyclerView.Adapter<LockAdapter.MyLockViewHolder> implements Filterable {
    private Context context;
    private ArrayList id, title, password, datetime, color, description;
    List<String> searchByName;

    public LockAdapter(Context context, ArrayList<String> id, ArrayList<String> title, ArrayList<String> password, ArrayList<String> datetime, ArrayList<String> color, ArrayList<String> description) {
        this.context = context;
        this.id = id;
        this.title = title;
        this.password = password;
        this.datetime = datetime;
        this.color = color;
        this.description = description;
        this.searchByName = new ArrayList<>(title);
    }

    @NonNull
    @Override
    public MyLockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_item_container_note,parent,false);
        return new MyLockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLockViewHolder holder, int position) {
        holder.textID.setText(String.valueOf(id.get(position)));
        holder.textTitle.setText(String.valueOf(title.get(position)));
        holder.textDateTime.setText(String.valueOf(datetime.get(position)));
        holder.layoutPassword.setBackgroundColor(Color.parseColor(String.valueOf(color.get(position))));

        holder.layoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LockTwo.class);
                intent.putExtra("id", String.valueOf(id.get(holder.getAdapterPosition())));
                intent.putExtra("title", String.valueOf(title.get(holder.getAdapterPosition())));
                intent.putExtra("datetime", String.valueOf(datetime.get(holder.getAdapterPosition())));
                intent.putExtra("color", String.valueOf(color.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return title.size();
    }

    @Override
    public Filter getFilter() {
        return titleFilter;
    }

    private Filter titleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(searchByName);
            }else {
                for (String title: searchByName){
                    if (title.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(title);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            title.clear();
            title.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyLockViewHolder extends RecyclerView.ViewHolder {
        TextView textID, textTitle, textDateTime;
        LinearLayout layoutPassword;

        public MyLockViewHolder(@NonNull View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.textID);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            layoutPassword = itemView.findViewById(R.id.layoutPassword);
        }
    }
}

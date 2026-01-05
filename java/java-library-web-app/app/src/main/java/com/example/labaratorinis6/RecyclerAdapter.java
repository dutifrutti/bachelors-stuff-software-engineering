package com.example.labaratorinis6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labaratorinis6.AccountingSystem.Category;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Category>  categoryList;
    public  RecyclerAdapter(ArrayList<Category> categoryList){
        this.categoryList = categoryList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
         private TextView nameText;
         public MyViewHolder (final View view){
             super (view);
             nameText=view.findViewById(R.id.textView2);
         }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = categoryList.get(position).getName();
        holder.nameText.setText(name);
    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

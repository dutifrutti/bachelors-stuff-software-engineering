package com.example.labaratorinis6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import com.example.labaratorinis6.AccountingSystem.Category;

import java.util.ArrayList;


public class CategoryActivity extends AppCompatActivity {
    private ArrayList<Category> categoryList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        recyclerView = findViewById(R.id.categoryView);
        setCategoryInfo();
        setAdapter();
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(categoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setCategoryInfo(){
        categoryList.add(new Category("vienas"));
        categoryList.add(new Category("vienas2"));
        categoryList.add(new Category("vienas3"));
        categoryList.add(new Category("vienas4"));
    }
}
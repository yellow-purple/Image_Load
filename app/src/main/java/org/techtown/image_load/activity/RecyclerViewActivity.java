package org.techtown.image_load.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import org.techtown.image_load.R;
import org.techtown.image_load.RecyclerViewAdapter;

import java.util.ArrayList;


public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager Manager;
    RecyclerViewAdapter adapter;
    ArrayList<String> ImgUrl=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent main_intent=getIntent();
        ImgUrl.addAll((ArrayList<String>) main_intent.getSerializableExtra("img"));

        recyclerView = findViewById(R.id.recyclerView);
        Manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(Manager);
        adapter = new RecyclerViewAdapter(ImgUrl, this);
        recyclerView.setAdapter(adapter);


    }
}





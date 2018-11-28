package com.akina.healthcenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class DatalistActivity extends AppCompatActivity {
    public static CustomRVAdapter adapter;
    Button m_add_btn;
    String datatype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        datatype = extras.getString(MainActivity.DATA_TYPE);

        m_add_btn = findViewById(R.id.add_btn);
        Button back = findViewById(R.id.back_btn);

        m_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        TextView title = findViewById(R.id.add_title);
        title.setText(datatype);


        RecyclerView recyclerView = findViewById(R.id.elems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new CustomRVAdapter(this, datatype);
        recyclerView.setAdapter(adapter);

        SwipeController swipeController = new SwipeController(this, recyclerView) {

            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeController.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeController.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                adapter.removeItem(pos);
                                MainActivity.updateData();
                            }
                        }
                ));
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatalistActivity.super.finish();
            }
        });

    }
    public void addData()
    {
        Intent intent = new Intent(this, DataAddActivity.class);
        Bundle extras = new Bundle();
        extras.putString(MainActivity.DATA_TYPE, datatype);
        intent.putExtras(extras);
        startActivity(intent);
    }

}

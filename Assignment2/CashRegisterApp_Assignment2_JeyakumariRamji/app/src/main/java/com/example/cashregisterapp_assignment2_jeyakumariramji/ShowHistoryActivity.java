package com.example.cashregisterapp_assignment2_jeyakumariramji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class ShowHistoryActivity extends AppCompatActivity implements HistoryRecyclerAdapter.ItemListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        RecyclerView recyclerView=findViewById(R.id.historyRecyclerView);
        HistoryRecyclerAdapter adapter=new HistoryRecyclerAdapter(((MyApp) getApplication()).historyList,this);
        adapter.listener=this;
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        // Set up Back button functionality
        ImageButton backArrowButton = findViewById(R.id.backArrowButton);
        backArrowButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onItemClicked(int pos) {
        Intent intent=new Intent(ShowHistoryActivity.this,DetailHistoryActivity.class);
        intent.putExtra("Index",pos);
        startActivity(intent);
    }
}

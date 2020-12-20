package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPanel extends AppCompatActivity {

    private Button buttonClientPanel;
    private Button buttonStartShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);

        buttonClientPanel = findViewById(R.id.btnClientPanel);
        buttonStartShopping = findViewById(R.id.btnStartShopping);

        buttonClientPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClientPanel();
            }
        });

        buttonStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShopping();
            }
        });
    }

    public void viewClientPanel() {

        Intent intent = new Intent(getApplicationContext(), ClientPanel.class);
        startActivity(intent);
    }

    public void startShopping() {

        Intent intent = new Intent(getApplicationContext(), Shopping.class);
        startActivity(intent);
    }

}
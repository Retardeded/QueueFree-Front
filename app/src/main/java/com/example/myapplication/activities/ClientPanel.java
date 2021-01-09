package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class ClientPanel extends AppCompatActivity {

    Button btnGoMainPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_panel);

        btnGoMainPanel = findViewById(R.id.btnGoToMainPanel);

        btnGoMainPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientPanel.this, MainPanel.class);
                startActivity(i);
                //finish();
            }
        });
    }
}
package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashBoard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        if(getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            fillLoginData(bundle);
        }

        Button btnGoLogin= findViewById(R.id.btnGoLogIn);

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoard.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void fillLoginData(Bundle bundle) {
        if(bundle.getString("userInfo")!= null)
        {

            String[] userInfo = bundle.getString("userInfo").split(" ");

            TextView userTxt = findViewById(R.id.etDashUsername);
            userTxt.setText(userInfo[0]);

            TextView passwordTxt = findViewById(R.id.etDashPassword);
            passwordTxt .setText(userInfo[1]);
        }
    }
}
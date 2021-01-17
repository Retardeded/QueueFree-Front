package com.example.myapplication.activities;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        if(getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            fillLoginData(bundle);
        }
        Button buttonGoLogin= findViewById(R.id.btnGoLogIn);
        buttonGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoard.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Show your data required for login
     */
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
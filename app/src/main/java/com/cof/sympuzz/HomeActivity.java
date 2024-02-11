package com.cof.sympuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView Username, Email;
    Button testbtn, seasonbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        testbtn = findViewById(R.id.testbtn);
        seasonbtn = findViewById(R.id.seasonbtn);

        showUserData();
    }

    public void showUserData(){

        Intent intent = getIntent();

        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        Username.setText(username);
        Email.setText(email);

    }

}
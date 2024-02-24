package com.cof.sympuzz;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {

    TextView Username, Email;
    Button button_test, button_season, button_search;
    ImageButton button_home, button_profile;
    Dialog dialog_season;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = getLayoutInflater();


        Username = findViewById(R.id.main_username);

        button_season = findViewById(R.id.button_season);
        button_profile = findViewById(R.id.button_profile);
        button_home = findViewById(R.id.button_home);
        button_search = findViewById(R.id.button_search);

        showUserData();

        button_season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setView(inflater.inflate(R.layout.activity_season, null));
                builder.create();
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(10);
                dialog.show();
            }
        });

        button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openProfileActivity(v);
            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openSearchActivity(v);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showUserData();
    }

    public void showUserData(){

        Intent intent = getIntent();

        String username = intent.getStringExtra("username");

        Username.setText(username);
    }
    private void openProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    private void openSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


}
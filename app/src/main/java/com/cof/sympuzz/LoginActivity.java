package com.cof.sympuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText Username, Password;
    TextView createacc;
    Button logbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        createacc = findViewById(R.id.createacc);
        logbtn = findViewById(R.id.logbtn);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()){

                } else {
                    checkUser();
                }
            }
        });

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUsername(){
        String val = Username.getText().toString();
        if (val.isEmpty()){
            Username.setError("Имя пользователя не может быть пустым");
            return false;
        } else {
            Username.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = Password.getText().toString();
        if (val.isEmpty()){
            Password.setError("Пароль не может быть пустым");
            return false;
        } else {
            Password.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String username = Username.getText().toString().trim();
        String password = Password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    Username.setError(null);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);

                    if (passwordFromDB.equals(password)){
                        Username.setError(null);

                        String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                        String dateofbirthFromDB = snapshot.child(username).child("dateofbirth").getValue(String.class);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("dateofbith", dateofbirthFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        Password.setError("Неверный пароль");
                        Password.requestFocus();
                    }
                } else {
                    Username.setError("Такого польхователя не существует");
                    Username.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
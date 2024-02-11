package com.cof.sympuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText Username, Email, DateOfBirth, Password;
    TextView logtext;
    Button regbtn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        DateOfBirth = findViewById(R.id.dateofbirth);
        Password = findViewById(R.id.password);
        logtext = findViewById(R.id.logtext);
        regbtn = findViewById(R.id.regbtn);
        
        regbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String username = Username.getText().toString();
                String email = Email.getText().toString();
                String dateofbirth = DateOfBirth.getText().toString();
                String password = Password.getText().toString();

                HelperClass helperClass = new HelperClass(username, email, dateofbirth, password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(RegisterActivity.this,"Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        logtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
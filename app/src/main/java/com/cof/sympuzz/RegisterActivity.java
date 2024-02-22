package com.cof.sympuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private EditText Username, Email, Password, ConfirmPassword;
    private ProgressBar progressBar;
    private RadioGroup Gender;
    private RadioButton GenderSelected;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressbar);
        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmpassword);

        Gender = findViewById(R.id.gender);
        Gender.clearCheck();

        Button ButtonRegister = findViewById(R.id.regbtn);
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int GenderSelectedID = Gender.getCheckedRadioButtonId();
                GenderSelected = findViewById(GenderSelectedID);

                String textUsername = Username.getText().toString();
                String textEmail = Email.getText().toString();
                String textPassword = Password.getText().toString();
                String textConfirmPassword = ConfirmPassword.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textUsername)) {
                    Username.setError("Имя пользователя не введено");
                    Username.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Email.setError("Почта не введена");
                    Email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Email.setError("Неверный формат");
                    Email.requestFocus();
                } else if (Gender.getCheckedRadioButtonId() == -1) {
                    GenderSelected.setError("Пол не выбран");
                    GenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Password.setError("Пароль не введён");
                    Password.requestFocus();
                } else if (textPassword.length() < 6) {
                    Password.setError("Пароль слишком короткий");
                    Password.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPassword)) {
                    ConfirmPassword.setError("Введите пароль повторно");
                    ConfirmPassword.requestFocus();
                } else if (!textPassword.equals(textConfirmPassword)) {
                    ConfirmPassword.setError("Пароли не совпадают");
                    ConfirmPassword.requestFocus();

                    Password.clearComposingText();
                    ConfirmPassword.clearComposingText();
                } else {
                    textGender = GenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textUsername, textEmail, textGender, textPassword);
                }
            }
        });
    }

    private void registerUser(String textUsername, String textEmail, String textGender, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textUsername).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textGender);

                            DatabaseReference refenceProfile = FirebaseDatabase.getInstance().getReference("Registered user");

                            refenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно. Подтвердите свою почту", Toast.LENGTH_LONG).show();

                                        /*
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        */
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Регистрация провалена", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);

                                }
                            });

                        } else {
                            try {
                                throw  task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Password.setError("Ваш пароль слишком лёгкий");
                                Password.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Email.setError("Такого адреса электронной почты не существует или он уже занят");
                                Email.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Email.setError("Пользователь уже зарегистрирован");
                                Email.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
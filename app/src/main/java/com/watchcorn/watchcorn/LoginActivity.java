package com.watchcorn.watchcorn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.SortedMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button login, signup;
    private DB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.EmailAddress);
        pass = findViewById(R.id.Password);
        login = findViewById(R.id.Login);
        signup = findViewById(R.id.SignUp);

        //instanciation de la classe DB (database)
        database = new DB(this);





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cursor pour parcourir la base de données
                Cursor res = database.GetData();

                String emailString = email.getText().toString();
                emailString = emailString.toLowerCase(Locale.ROOT);
                String passString = pass.getText().toString();

                //Des variables pour stocker les infos depuis la base de données
                String  A = "variable pour le 1er champ de la base de données",
                        B = "variable pour le 2éme champ de la base de données",
                        C = "variable pour le 3éme champ de la base de données";

                while (res.moveToNext()) {
                    if (emailString.equals(res.getString(0)) && passString.equals(res.getString(1))) {
                        A = res.getString(0);
                        B = res.getString(1);
                        C = res.getString(3);
                    }
                }

                if (emailString.equals(A) && passString.equals(B)) {
                    if (C.equals("1")) {
                        Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        database.UpdateFirstTime(emailString,"1");
                    }
                } else if (emailString.isEmpty() || passString.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Warning !");
                    builder.setMessage("All fields are required ");
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Warning !");
                    builder.setMessage("Email or Password is incorrect");
                    builder.show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });
    }



}
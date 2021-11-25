package com.watchcorn.watchcorn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private DB database;
    private EditText mfullname, memail, mpass, mretapepass;
    private Button savebutton;
    private int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mfullname = findViewById(R.id.PersonName);
        memail = findViewById(R.id.EmailAddress);
        mpass = findViewById(R.id.Password);
        mretapepass = findViewById(R.id.confirmed_password);
        savebutton = findViewById(R.id.save_button);
        database = new DB(this);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fnTXT = mfullname.getText().toString();
                String emailTXT = memail.getText().toString();
                String passTXT = mpass.getText().toString();
                String retappassTXT = mretapepass.getText().toString();

                if(fnTXT.isEmpty() || emailTXT.isEmpty() || passTXT.isEmpty() || retappassTXT.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Warning !");
                    builder.setMessage("Please enter all fields !");
                    builder.show();
                }else{
                    if (!passTXT.equals(retappassTXT)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Warning !");
                        builder.setMessage("Passwords do not match!");
                        builder.show();
                    } else {
                        Boolean CheckInsertData = database.InsertUserData(emailTXT.toLowerCase(Locale.ROOT), passTXT, fnTXT,j);
                        if (CheckInsertData) {
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            j++;
                        } else {
                            Toast.makeText(SignupActivity.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
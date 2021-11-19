package com.watchcorn.watchcorn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private DB database;
    private EditText mfullname, memail, mpass, mretapepass;
    private Button savebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mfullname = findViewById(R.id.PersonName);
        memail = findViewById(R.id.EmailAddress);
        mpass = findViewById(R.id.Password);
        mretapepass = findViewById(R.id.Password2);
        savebutton = findViewById(R.id.save_button);
        database = new DB(this);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fnTXT = mfullname.getText().toString();
                String emailTXT = memail.getText().toString();
                String passTXT = mpass.getText().toString();
                String retappassTXT = mretapepass.getText().toString();

                if (!passTXT.equals(retappassTXT)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Warning !");
                    builder.setMessage("ConfirmPassword is wrong ! ");
                    builder.show();
                } else {
                    Boolean CheckInsertData = database.InsertUserData(emailTXT, passTXT, fnTXT);
                    if (CheckInsertData) {
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "The address email already exist!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
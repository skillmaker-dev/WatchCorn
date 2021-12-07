package com.watchcorn.watchcorn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private DB database;
    private EditText mfullname, memail, mpass, mretapepass;
    private Button savebutton;

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListner);
        super.onStop();
    }

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
                } else {
                    if(Patterns.EMAIL_ADDRESS.matcher(emailTXT).matches()){
                        if (!passTXT.equals(retappassTXT)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("Warning !");
                            builder.setMessage("Passwords do not match!");
                            builder.show();
                        } else if (passTXT.length() < 8) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("Warning !");
                            builder.setMessage("Password too short (8 characters at least) !");
                            builder.show();
                        } else {
                            Boolean CheckInsertData = database.InsertUserData(emailTXT.toLowerCase(Locale.ROOT), passTXT, fnTXT);
                            if (CheckInsertData) {
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupActivity.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setTitle("Error");
                        builder.setIcon(R.drawable.error);
                        builder.setMessage("Invalid input Email !! ");
                        builder.setCancelable(false);
                        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });
                        builder.show();
                    }
                }
            }
        });
    }
}
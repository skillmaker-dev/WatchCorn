package com.watchcorn.watchcorn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button login, signup;
    private ImageView finger;
    private DB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.EmailAddress);
        pass = findViewById(R.id.Password);
        login = findViewById(R.id.Login);
        signup = findViewById(R.id.SignUp);
        finger = findViewById(R.id.fingerprint);

        //instanciation de la classe DB (database)
        database = new DB(this);

        Cursor res = database.GetData();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cursor pour parcourir la base de données


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
                        Intent i = new Intent(LoginActivity.this, Test.class);
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
        
        //start create biometric dialog box: First we need an executor:
        Executor executor = ContextCompat.getMainExecutor(this);
        //now we need to create the biometric prompt callback
        //this will give us the result of the authentication and if we can login or nit
        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),"Login Success !",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        //let's create our biometric dialog
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your fingerprint to login to your app ")
                .setNegativeButtonText("Cancel")
                .build();
        
        //Now everything is ready
        finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
        
        
    }

}
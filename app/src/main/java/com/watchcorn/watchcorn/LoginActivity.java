package com.watchcorn.watchcorn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;

import java.util.SortedMap;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button login;
    private TextView signup;
    private ImageView finger;
    private DB database;
    int count = 0;

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    public void onBackPressed() {

        count++;

        if (count == 1)
            Toast.makeText(getApplicationContext(), "Press Back Again To Exit", Toast.LENGTH_SHORT).show();

        if (count == 2)
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationLogin();
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

                AuthenticationLoginWithFinger();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        Cursor res = database.GetData();

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                finger.setVisibility(View.VISIBLE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                finger.setVisibility(View.INVISIBLE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                finger.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"The biometric sensors is currently unavailable",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                finger.setVisibility(View.INVISIBLE);

                if(res.getCount() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setCancelable(true);
                    builder.setIcon(R.drawable.ic_baseline_info_24);
                    builder.setTitle("Information");
                    builder.setMessage("You have the possibility to use your fingerprint to login, please save your fingerprint in your phone ");
                    builder.show();
                }

                break;

        }

        res.close();

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
                   /* Log.d("movies : ", movie.getTitle());*/

    public void AuthenticationLogin(){
        //Cursor pour parcourir la base de données
        Cursor res = database.GetData();

        String emailString = email.getText().toString();
        emailString = emailString.toLowerCase(Locale.ROOT);
        String passString = pass.getText().toString();

        //Des variables pour stocker les infos depuis la base de données
        String A = null, B = null, C = null;

        while (res.moveToNext()) {
            if (emailString.equals(res.getString(1)) && passString.equals(res.getString(2))) {
                A = res.getString(1);
                B = res.getString(2);
                C = res.getString(4);
            }
        }

        res.close();

        if(Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            if (emailString.equals(A) && passString.equals(B)) {
                database.UpdateStatus(emailString,"online");
                if (C.equals("1")) {
                    Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    database.UpdateFirstTime(emailString, "1");
                    database.initializeGenresTable();
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
        }else{
            Toast.makeText(LoginActivity.this,"Invalid Email Adress!!",Toast.LENGTH_SHORT).show();
        }

    }

    public void AuthenticationLoginWithFinger(){
        //Cursor pour parcourir la base de données
        Cursor res = database.GetData();

        while (res.moveToNext()) {
            if (res.getString(0).equals("1")) {
               email.setText(res.getString(1));
               pass.setText(res.getString(2));
            }
        }
        res.close();

        AuthenticationLogin();
    }

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
}
package com.watchcorn.watchcorn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

public class UserActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private EditText fullname, email,password;
    private Button updatebtn,logoutbtn;
    private DB database;

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        fullname = findViewById(R.id.fullnameUser);
        email = findViewById(R.id.emailUser);
        password = findViewById(R.id.passwordUser);
        updatebtn = findViewById(R.id.changePassword);
        logoutbtn = findViewById(R.id.logoutBttn);
        database = new DB(this);

        // initialize the nav
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        // set home as default selected
        bottomNavigationView.setSelectedItemId(R.id.user);
        // the listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search_List_Activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.watchList:
                        startActivity(new Intent(getApplicationContext(), WatchListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorits:
                        startActivity(new Intent(getApplicationContext(), FavoritsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user:
                        return true;
                }
                return false;
            }
        });

        Cursor res= database.GetData();
        String A = null, B = null , C = null;

        while(res.moveToNext()){
           if(res.getString(5).equals("online")){
                A = res.getString(3);
                B = res.getString(1);
                C = res.getString(2);
           }
        }

        fullname.setText(A);
        email.setText(B);
        password.setText(C);


        String finalB = B;
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res= database.GetData();

                while(res.moveToNext()){
                    if(res.getString(5).equals("online")){
                       database.UpdateStatus(finalB,"offline");
                    }
                }
                Intent i = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  A1 = fullname.getText().toString(),
                        B1 = email.getText().toString(),
                        C1 = password.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to save change");
                builder.setIcon(R.drawable.ic_baseline_info_24);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.UpdateUserinfos(finalB,B1,C1,A1);
                        Toast.makeText(UserActivity.this,"Changed Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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
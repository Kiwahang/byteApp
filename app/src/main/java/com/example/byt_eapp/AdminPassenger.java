package com.example.byt_eapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminPassenger extends AppCompatActivity {

    private Button btn_Admin, btn_Passenger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_passenger);
        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
        //mode_private lets no other app to access data

        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.apply();
        if(sp.contains("adminusername")){
            Intent intent = new Intent(AdminPassenger.this, AdminHome.class);
            startActivity(intent);
            finish();
        }
        else {


            initViews();

            btn_Passenger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminPassenger.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btn_Admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminPassenger.this, AdminLogIn.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void initViews() {
        Log.d(TAG, "initViews: Started");
        btn_Admin = findViewById(R.id.btn_Admin);
        btn_Passenger = findViewById(R.id.btn_Passenger);
    }


}
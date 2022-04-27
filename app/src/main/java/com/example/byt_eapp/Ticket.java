package com.example.byt_eapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Ticket extends AppCompatActivity {

    public static TextView txtBusNameTicket, txtBusNoTicket, txtRouteNameTicket, txtFromTicket, txtToTicket, txtTotalTicket;

    public static EditText edtTxtDateTicket, edtTxtTimeTicket;

    public static Button btnOkTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        initViews();
        //USING SHAREDPREFERENCE FOR SAVING CREDENTIALS
        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
        //mode_private lets no other app to access data
        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("bus",name);

        txtBusNoTicket.setText(sp.getString("busno",""));
        txtFromTicket.setText(sp.getString("from_location",""));
        txtToTicket.setText(sp.getString("to_location",""));
        txtTotalTicket.setText(sp.getString("total_amount",""));
        editor.apply();

    }

    private void initViews() {
        txtBusNameTicket = findViewById(R.id.txtBusNameTicekt);
        txtBusNoTicket = findViewById(R.id.txtBusNoTicket);
        txtRouteNameTicket = findViewById(R.id.txtRouteNameTicket);
        txtFromTicket = findViewById(R.id.txtFromTicket);
        txtToTicket = findViewById(R.id.txtToTicket);
        txtTotalTicket = findViewById(R.id.txtTotalTicket);
        edtTxtDateTicket = findViewById(R.id.edtTxtDateTicket);
        edtTxtTimeTicket = findViewById(R.id.edtTxtTimeTicket);

        btnOkTicket = findViewById(R.id.btnOkTicket);
    }
}
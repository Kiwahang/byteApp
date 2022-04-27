package com.example.byt_eapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.util.Map;

public class Ticket extends AppCompatActivity {

    public static TextView txtBusNameTicket, txtBusNoTicket, txtRouteNameTicket, txtFromTicket, txtToTicket, txtTotalTicket;

    public static EditText edtTxtDateTicket, edtTxtTimeTicket;

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
        KhaltiButton khaltiButton = findViewById(R.id.khalti_button);

        khaltiImplement(khaltiButton,sp.getString("email",""),Long.parseLong(sp.getString("total_amount","")));

    }

    public void khaltiImplement(KhaltiButton kBuy,String email,Long amount){

        Config.Builder builder = new Config.Builder(Constant.pub, email, "", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        });

        Config config = builder.build();
        kBuy.setCheckOutConfig(config);
        KhaltiCheckOut khaltiCheckOut1 = new KhaltiCheckOut(this,config);
        kBuy.setOnClickListener(v -> khaltiCheckOut1.show());
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
    }
}
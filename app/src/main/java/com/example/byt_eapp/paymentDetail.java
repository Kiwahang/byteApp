package com.example.byt_eapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.navigation.NavigationBarView;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class paymentDetail extends AppCompatActivity {

    public static TextView txtBusName, txtBusNo, txtRouteName, txtActualPrice, txtDiscount, txtTotal;
    public static EditText edtTxtDate, edtTxtTime;

    public static Spinner spinnerFrom, spinnerTo;
    public static Button payment_modal;

    Connection connect;
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        initViews();


        txtBusNo = findViewById(R.id.txtBusNo);
        Intent intent = getIntent();
        String bus_name = intent.getStringExtra(payment.BUS_NAME);
        txtBusNo.setText(bus_name);


        fillSpinner("from_stop",spinnerFrom);
        fillSpinner("to_stop",spinnerTo);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String from_selected = spinnerFrom.getSelectedItem().toString();
                String to_selected = spinnerTo.getSelectedItem().toString();

                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    if (connect != null) {
                        Log.d("msg", "connected");
                        String query = "SELECT amount FROM dbo.route_rate WHERE from_stop = '" + from_selected + "' AND to_stop = '" + to_selected + "'";
                        Statement statement = connect.createStatement();
                        ResultSet rs = statement.executeQuery(query);
                        while (rs.next()) {
                            txtActualPrice.setText(rs.getString("amount"));
                            txtTotal.setText(rs.getString("amount"));
                            break;
                        }

                    } else {
                        ConnectionResult = "check connection";
                    }
                    connect.close();
                } catch (Exception ex) {
                    Log.e("Error ", ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String from_selected = spinnerFrom.getSelectedItem().toString();
                String to_selected = spinnerTo.getSelectedItem().toString();

                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    if (connect != null) {
                        Log.d("msg", "connected");
                        String query = "SELECT amount FROM dbo.route_rate WHERE from_stop = '" + from_selected + "' AND to_stop = '" + to_selected + "'";
                        Statement statement = connect.createStatement();
                        ResultSet rs = statement.executeQuery(query);
                        while (rs.next()) {
                            txtActualPrice.setText(rs.getString("amount"));
                            txtTotal.setText(rs.getString("amount"));
                            break;
                        }

                    } else {
                        ConnectionResult = "check connection";
                    }
                    connect.close();
                } catch (Exception ex) {
                    Log.e("Error ", ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        payment_modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from_destination = spinnerFrom.getSelectedItem().toString();
                String to_destination = spinnerTo.getSelectedItem().toString();
                String actual_amount = txtActualPrice.getText().toString();
                String total_amount = txtTotal.getText().toString();
                //USING SHARED PREFERENCE FOR SAVING CREDENTIALS
                SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
                //mode_private lets no other app to access data
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("busno",bus_name);
                editor.putString("from_location",from_destination);
                editor.putString("to_location",to_destination);
                editor.putString("actual_amount",actual_amount);
                editor.putString("total_amount",total_amount);
                editor.apply();
                KhaltiPayDialog dialog = new KhaltiPayDialog();
                dialog.show(getSupportFragmentManager(),"paymentMethod");
            }
        });


    }

    public void fillSpinner(String destination,Spinner spinner_name){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                Log.d("msg", "connected");
                String query = "Select "+destination+" from dbo.route_rate";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                ArrayList<String> data = new ArrayList<String>();

                while(rs.next()){
                    String destination_name = rs.getString(destination);
                    if(!data.contains(destination_name)){
                        data.add(destination_name);
                    }

                }
                ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
                spinner_name.setAdapter(array);

            } else {
                ConnectionResult = "check connection";
            }
            connect.close();
        } catch (Exception ex) {
            Log.e("Error ", ex.getMessage());
        }
    }



    private void initViews() {
        txtBusNo = findViewById(R.id.txtBusN);
        txtBusName =findViewById(R.id.txtBusName);
        txtRouteName = findViewById(R.id.txtRouteName);
        txtActualPrice = findViewById(R.id.txtActualPrice);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtTotal = findViewById(R.id.txtTotal);

        edtTxtDate = findViewById(R.id.edtTxtDate);
        edtTxtTime = findViewById(R.id.edtTxtTime);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        payment_modal = findViewById(R.id.payment_modal);
    }
}
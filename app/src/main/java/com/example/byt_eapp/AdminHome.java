package com.example.byt_eapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class AdminHome extends AppCompatActivity {
    Connection connect;
    String ConnectionResult = "";
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_home);
        SimpleAdapter ad;

        //list
        ListView list = (ListView) findViewById(R.id.listview);

        List<Map<String,String>> MyDataList = null;
        ListItem MyData = new ListItem();
        MyDataList = MyData.getlist();

        String[] from = {"Username","Email"};
        int[] to = {R.id.textView6,R.id.textView8};
        ad = new SimpleAdapter(this,MyDataList,R.layout.list_item,from,to);
        list.setAdapter(ad);


        btn = findViewById(R.id.button);
//        tx2.setText("Hello, "+sp.getString("adminusername",""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(AdminHome.this, AdminPassenger.class);
                startActivity(intent);
                finish();
//                try {
//                    ConnectionHelper connectionHelper = new ConnectionHelper();
//                    connect = connectionHelper.connectionclass();
//                    if (connect != null) {
//                        Log.d("msg", "connected");
//                        String query = "Select * from dbo.admin";
//                        Statement st = connect.createStatement();
//                        ResultSet rs = st.executeQuery(query);
//
//                        while (rs.next()) {
//                            tx1.setText(rs.getString(2));
//                            tx2.setText(rs.getString(3));
//                        }
//                    } else {
//                        ConnectionResult = "check connection";
//                    }
//                } catch (Exception ex) {
//                    Log.e("Error ", ex.getMessage());
//                }

            }
        });


//        forlogout // deleting sharedpreferences
//        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.apply();
    }

//    @Override
//    public void onBackPressed() {
//        //doesnot allow back
////        super.onBackPressed();
//    }
}
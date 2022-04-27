package com.example.byt_eapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminLogIn extends AppCompatActivity{

    private EditText edtTxtAdminUsername, edtTxtAdminPassword;
    private Button btnAdminLogin;
    public EditText adminName;
    public EditText adminPassword;
    Connection connect;
    String ConnectionResult = "";
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_in);
        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
        //mode_private lets no other app to access data

        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.apply();
        if(sp.contains("adminusername")){
            Intent intent = new Intent(AdminLogIn.this, AdminHome.class);
            startActivity(intent);
            finish();
        }

//        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
//        String data = sp.getString("username","");
//        if(!data.isEmpty()){
//            Intent intent = new Intent(AdminLogIn.this, AdminHome.class);
//            startActivity(intent);
//        }
        initViews();

//        checkloginstatus();



        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                Boolean isSuccess = false;
                String name = adminName.getText().toString();
                String password = adminPassword.getText().toString();

                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AdminLogIn.this, "Either name or password field is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        ConnectionHelper connectionHelper = new ConnectionHelper();
                        connect = connectionHelper.connectionclass();
                        if (connect != null) {
                            Log.d("msg", "connected");
                            String query = "Select * from dbo.admin where name='"+name+"' and password='"+password+"'";
                            Statement st = connect.createStatement();
                            ResultSet rs = st.executeQuery(query);
                            if(rs.next()){
                                ConnectionResult="login successful";
                                isSuccess = true;

                                //USING SHAREDPREFERENCE FOR SAVING CREDENTIALS
                                SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
                                //mode_private lets no other app to access data
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("adminusername",name);
                                editor.putString("adminpassword",password);
                                editor.apply();
                                Intent intent = new Intent(AdminLogIn.this, AdminHome.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(AdminLogIn.this, "Either name or password doesnot match", Toast.LENGTH_SHORT).show();
                            }

                        /*while (rs.next()) {
                            String dbName = rs.getString(2);
                            String dbPassword = rs.getString(3);
                            boolean nameEqual = dbName.equals(name);
                            boolean passEqual = dbPassword.equals(password);
                            if (nameEqual && passEqual) {

                                Intent intent = new Intent(AdminLogIn.this, AdminHome.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AdminLogIn.this, "Either name or password doesnot match", Toast.LENGTH_SHORT).show();
                            }
                        }*/
                        } else {
                            ConnectionResult = "check connection";
                        }
                        connect.close();
                    } catch (Exception ex) {
                        isSuccess=false;
                        Log.e("Error ", ex.getMessage());
                    }
                }
                }

        });

    }

    private void initViews(){
        Log.d(TAG, "initViews: Started");

        adminName =  findViewById(R.id.edtTxtAdminUsername);
        adminPassword = findViewById(R.id.edtTxtAdminPassword);

        btnAdminLogin = findViewById(R.id.btnAdminLogin);
    }
//    public void checkloginstatus(){
//        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
//        if(sp.contains("username")){
//            Intent intent = new Intent(AdminLogIn.this, AdminHome.class);
//            startActivity(intent);
//        }
//    }

}

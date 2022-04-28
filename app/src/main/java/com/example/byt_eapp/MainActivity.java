 package com.example.byt_eapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

 public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText edtTxtEmail, edtTxtPassword;
    private Button btnLogin;
    private TextView txtForgotPassword, txtSignup;
    private ConstraintLayout parent;
     Connection connect;
     String ConnectionResult = "";
     int p_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
        //mode_private lets no other app to access data

        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.apply();
        if(sp.contains("email")){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
                String email = edtTxtEmail.getText().toString();
                String password = edtTxtPassword.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        ConnectionHelper connectionHelper = new ConnectionHelper();
                        connect = connectionHelper.connectionclass();
                        if (connect != null) {
                            Log.d("msg", "connected");
                            String query = "SELECT * FROM dbo.passengers WHERE email = '" + email + "' AND password = '" + password + "'";
                            Statement statement = connect.createStatement();
                            ResultSet rs = statement.executeQuery(query);
                            int i = 0;

                            while (rs.next()) {
                                p_id=rs.getInt("passenger_id");
                                i++;
                            }

                            if (i > 0) {
                                //USING SHAREDPREFERENCE FOR SAVING CREDENTIALS
                                SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
                                //mode_private lets no other app to access data
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("email",email);
                                editor.putString("password",password);
                                editor.putInt("id",p_id);
                                editor.apply();
                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(MainActivity.this, "Email or Password Doesnot match", Toast.LENGTH_SHORT).show();
                                System.out.println("failed");
                            }

                        } else {
                            ConnectionResult = "check connection";
                        }
                        connect.close();
                    } catch (Exception ex) {
                        Log.e("Error ", ex.getMessage());
                    }
                }
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

    }
    private void initViews() {
        Log.d(TAG, "initViews: Started");
        edtTxtEmail = findViewById(R.id.edtTxtLoginEmail);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtSignup = findViewById(R.id.txtSignUp);

        parent = findViewById(R.id.parent);
    }
}
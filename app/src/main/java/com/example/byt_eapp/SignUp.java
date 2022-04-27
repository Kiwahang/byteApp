package com.example.byt_eapp;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";

    private EditText edtTxtFullName, edtTxtPhone, edtTxtEmail, edtTxtRegisterUsername, edtTxtRegisterPassword, edtTxtRegisterRePassword;
    private Button btnRegister;
    private RadioGroup rbGender;
    Connection connect;
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                For radio button
                RadioButton selectedRadioButton;
                selectedRadioButton = (RadioButton)findViewById(rbGender.getCheckedRadioButtonId());
                String gender = selectedRadioButton.getText().toString();

                String fullname = edtTxtFullName.getText().toString();
                String phone = edtTxtPhone.getText().toString();
                String email = edtTxtEmail.getText().toString();
                String username = edtTxtRegisterUsername.getText().toString();
                String password = edtTxtRegisterPassword.getText().toString();
                String repassword = edtTxtRegisterRePassword.getText().toString();
                if(fullname.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUp.this, "Please enter some data", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!password.equals(repassword)){
                        Toast.makeText(SignUp.this, "Password didnot match", Toast.LENGTH_SHORT).show();
                        System.out.println("password didnot match");
                    }
                    else{
                        try {
                            ConnectionHelper connectionHelper = new ConnectionHelper();
                            connect = connectionHelper.connectionclass();
                            if (connect != null) {
                                Log.d("msg", "database connected");
                                String query = "INSERT INTO dbo.passengers(full_name,gender,email,phone_number,password)" + "VALUES(?,?,?,?,?)";
                                PreparedStatement st = connect.prepareStatement(query);
                                st.setString(1, fullname);
                                st.setString(2, gender);
                                st.setString(3, email);
                                st.setString(4, phone);
                                st.setString(5, password);
                                int rows = st.executeUpdate();
                                if(rows > 0) {
                                    Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
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
            }
        });


    }

    private void initViews(){
        Log.d(TAG, "initViews: Started");
        edtTxtFullName = findViewById(R.id.edtTxtFullName);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtRegisterUsername = findViewById(R.id.edtTxtRegisterUsername);
        edtTxtRegisterPassword = findViewById(R.id.edttxtRegisterPassword);
        edtTxtRegisterRePassword = findViewById(R.id.edtTxtRegisterRePassword);

        rbGender = findViewById(R.id.rbGender);

        btnRegister = findViewById(R.id.btnRegister);
    }
}
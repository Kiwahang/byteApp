package com.example.byt_eapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class changePassword extends AppCompatActivity {

    private EditText edtTxtOldPassword, edtTxtNPassword, edtTxtNPassword2;

    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initViews();

        //database connection
        final Connection[] connect = new Connection[1];
        final String[] ConnectionResult = {""};



        //USING SHAREDPREFERENCE FOR SAVING CREDENTIALS
        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
        //mode_private lets no other app to access data
        SharedPreferences.Editor editor = sp.edit();
        String stored_email = sp.getString("email","");
        String stored_password = sp.getString("password","");
        editor.apply();



        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String old_password = edtTxtOldPassword.getText().toString();
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect[0] = connectionHelper.connectionclass();
                    if (connect[0] != null) {
                        Log.d("msg", "database connected");
                        String query = "select password from dbo.passengers where email='" + stored_email + "'";
                        Statement statement = connect[0].createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            String db_password = resultSet.getString("password");
                            if(old_password.equals(db_password)){
                                String new_password1 = edtTxtNPassword.getText().toString();
                                String new_password2 = edtTxtNPassword2.getText().toString();
                                if(new_password1.equals(new_password2)){
                                    String newq = "update dbo.passengers set password='"+new_password1+"' where email='"+stored_email+"'";
                                    Statement statement1 = connect[0].createStatement();
                                    statement1.executeUpdate(newq);
                                    Intent intent = new Intent(changePassword.this, HomeActivity.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(changePassword.this, "New passwords didnot match", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(changePassword.this, "Old password didnot match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        ConnectionResult[0] = "check connection";
                    }
                    connect[0].close();
                    Toast.makeText(changePassword.this, "Password has been changed successfully.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex) {
                    Log.e("Error ", ex.getMessage());
                }
            }
        });

    }

    private void initViews() {
        Log.d("Error", "initViews Started");

        edtTxtOldPassword = findViewById(R.id.edtTxtOldPassword);
        edtTxtNPassword = findViewById(R.id.edtTxtNPassword);
        edtTxtNPassword2 = findViewById(R.id.edtTxtNPassword2);

        btnChangePassword = findViewById(R.id.btnChangePassword);
    }
}
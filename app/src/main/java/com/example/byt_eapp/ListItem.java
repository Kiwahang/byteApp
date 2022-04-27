package com.example.byt_eapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListItem {
    Connection connect;
    String ConnectionResult="";
    Boolean isSuccess = false;

    public List<Map<String,String>>getlist(){
        List<Map<String,String>>data = null;
        data = new ArrayList<Map<String,String>>();
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect != null){
                String query = "Select * from dbo.passengers";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    Map<String,String> dtname = new HashMap<String,String>();
                    dtname.put("Username",resultSet.getString("full_name"));
                    dtname.put("Email",resultSet.getString("email"));
                    data.add(dtname);
                }
                ConnectionResult = "success";
                isSuccess = true;
                connect.close();
            }
            else{
                ConnectionResult = "Failed";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }
}

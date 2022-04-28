package com.example.byt_eapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class KhaltiPayDialog extends BottomSheetDialogFragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.payment_choose_layout,container,false);

        KhaltiButton kBuy = v.findViewById(R.id.khalti_button);
        //USING SHARED PREFERENCE FOR SAVING CREDENTIALS
        SharedPreferences sp = this.getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        //mode_private lets no other app to access data
        SharedPreferences.Editor editor = sp.edit();
        String totalamount = sp.getString("total_amount","");
        int id = sp.getInt("id",0);
        khaltiImplement(kBuy,sp.getString("email",""),sp.getString("busno",""),Long.parseLong(sp.getString("total_amount",""))*100,id,totalamount);
        return v;
        }

    public void khaltiImplement(KhaltiButton kBuy,String email,String busNo,Long amount,int id,String total){

        Config.Builder builder = new Config.Builder(Constant.pub, email, busNo, amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                try {

                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    Connection connect = connectionHelper.connectionclass();
                    if (connect != null) {
                        Log.d("msg", "payment connected");
                        String query = "INSERT INTO dbo.ticket(price,passenger_id)" + " VALUES(?,?)";
                        PreparedStatement st = connect.prepareStatement(query);
                        st.setString(1,total);
                        st.setInt(2,id);
                        st.executeUpdate();


                    } else {
                    }
                    connect.close();
                } catch (Exception ex) {
                    Log.e("Error ", ex.getMessage());
                }
                Log.i("Payment Success", data.toString());
                Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Ticket.class);
                startActivity(intent);
            }
        });

        Config config = builder.build();
        kBuy.setCheckOutConfig(config);
        KhaltiCheckOut khaltiCheckOut1 = new KhaltiCheckOut(this.getContext(),config);
        kBuy.setOnClickListener(v -> khaltiCheckOut1.show());

    }
}

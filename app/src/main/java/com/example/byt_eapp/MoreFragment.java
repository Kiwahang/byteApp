package com.example.byt_eapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MoreFragment extends Fragment {
    private static final String TAG = "MoreFragment";

    private TextView app_update, change_password, TermsConditions, AboutUs, LogOut;


   View view;

   public MoreFragment(){

   }

   public static MoreFragment newInstance(String param1,String param2){
       MoreFragment fragment = new MoreFragment();
       return fragment;
   }

   @Override
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
   }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);
        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LogOut = view.findViewById(R.id.LogOut);
        change_password = view.findViewById(R.id.change_password);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.clear();
                editor.apply();
                Intent intent = new Intent(getActivity(), AdminPassenger.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), changePassword.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

}
package com.example.byt_eapp;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.budiyev.android.codescanner.CodeScanner;
//import com.budiyev.android.codescanner.CodeScannerView;
//import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;


public class PaymentFragment extends Fragment{

//    private CodeScanner mCodeScanner;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        return view;

//        final Activity activity = getActivity();
//        View root = inflater.inflate(R.layout.fragment_payment,container,false);
//        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
//        mCodeScanner = new CodeScanner(activity,scannerView);
//        mCodeScanner.setDecodeCallback(new DecodeCallback() {
//            @Override
//            public void onDecoded(@NonNull Result result) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCodeScanner.startPreview();
//            }
//        });
//        return root;

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    public void onPause(){
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }


}
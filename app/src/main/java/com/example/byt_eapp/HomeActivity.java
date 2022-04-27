package com.example.byt_eapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.byt_eapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragement(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            System.out.println(item);
            int id = item.getItemId();
            switch (id) {

                case R.id.home:
                    replaceFragement(new HomeFragment());
                    break;

                case R.id.inbox:
                    replaceFragement(new InboxFragment());
                    break;

                case R.id.payment:
                    Intent intent = new Intent(this, payment.class);
                    startActivity(intent);
//                    replaceFragement(new PaymentFragment());
                    break;

                case R.id.history:
                    replaceFragement(new HistoryFragment());
                    break;

                case R.id.more:
                    replaceFragement(new MoreFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragement(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}
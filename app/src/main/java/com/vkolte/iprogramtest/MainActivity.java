package com.vkolte.iprogramtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.vkolte.iprogramtest.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Context context;
    private CompareAdapter compareAdapter;
    private APICall apiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        callImageAPAI();


    }

    private void callImageAPAI() {
        try{
            apiCall = new APICall(context);
            apiCall.init(new APICall.APIResult() {
                @Override
                public void onSuccess(ArrayList<CompareModel> compareModelArrayList) {

                    showList(compareModelArrayList);

                }

                @Override
                public void onError() {

                    try{
                        binding.progressBar.setVisibility(View.GONE);
                        binding.errorMessage.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            apiCall.getImageData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showList(ArrayList<CompareModel> compareModelArrayList) {
        try{
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            try {
                binding.recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                binding.recyclerView.setLayoutManager(linearLayoutManager);
                try {

                    compareAdapter = new CompareAdapter(context, compareModelArrayList);
                    binding.recyclerView.setAdapter(compareAdapter);

                } catch (Exception e) {
                    e.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
                binding.errorMessage.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        DBHelper dbHelper = new DBHelper(context);
//        dbHelper.dropTableIfExist();
    }
}
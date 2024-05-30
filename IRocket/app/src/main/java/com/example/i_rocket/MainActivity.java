package com.example.i_rocket;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.i_rocket.fragment.HomeFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private View loading;
    private RelativeLayout rl_loading;
    private FrameLayout fr_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = findViewById(R.id.ma_loading);
        rl_loading = findViewById(R.id.rl_loding);
        fr_fragment = findViewById(R.id.frame_container);

        new Handler().postDelayed(this::hideloding, 1500);
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();

        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());



        if (!(fragment instanceof HomeFragment)){
            Log.d("TAG3", "jalan: ");
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
    private void showloding(){
        loading.setVisibility(View.VISIBLE);

    }
    private  void hideloding(){
        loading.setVisibility(View.GONE);
        rl_loading.setVisibility(View.GONE);
        fr_fragment.setVisibility(View.VISIBLE);

    }
    
}
package com.example.i_rocket;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.i_rocket.fragment.HomeFragment;


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
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, homeFragment, "HOME")
                    .addToBackStack(null)
                    .commit();
        }

    }
    private  void hideloding(){
        loading.setVisibility(View.GONE);
        rl_loading.setVisibility(View.GONE);
        fr_fragment.setVisibility(View.VISIBLE);

    }
    
}
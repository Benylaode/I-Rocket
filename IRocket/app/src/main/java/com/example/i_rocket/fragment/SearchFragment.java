package com.example.i_rocket.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.i_rocket.ApiService;
import com.example.i_rocket.R;
import com.example.i_rocket.RetrofitClient;

import retrofit2.Retrofit;

public class SearchFragment extends Fragment {
    private RelativeLayout rl_home_nav;
    private RelativeLayout rl_search_nav;
    private RelativeLayout rl_offline_nav;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seacrh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = RetrofitClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        ImageView iv_home = view.findViewById(R.id.IV_Home);
        ImageView iv_offline = view.findViewById(R.id.IV_offline);
        ImageView iv_search = view.findViewById(R.id.iv_search);

        rl_home_nav = view.findViewById(R.id.rl_home);
        rl_search_nav = view.findViewById(R.id.rl_search);
        rl_offline_nav = view.findViewById(R.id.rl_offline);

        rl_search_nav.setBackgroundResource(R.drawable.press_view);

        iv_offline.setOnClickListener(v -> {
            rl_offline_nav.setBackgroundResource(R.drawable.press_view);
            OfflineFragment offlineFragment = new OfflineFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, offlineFragment)
                    .addToBackStack(null)
                    .commit();
        });


        iv_search.setOnClickListener(v -> {
            rl_search_nav.setBackgroundResource(R.drawable.press_view);
            SearchFragment searchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });

        iv_home.setOnClickListener(v -> {
            rl_home_nav.setBackgroundResource(R.drawable.press_view);
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}

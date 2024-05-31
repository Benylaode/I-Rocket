package com.example.i_rocket.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_rocket.ApiService;
import com.example.i_rocket.Expedition;
import com.example.i_rocket.ExpeditionAdapter;
import com.example.i_rocket.ExpeditionResponse;
import com.example.i_rocket.R;
import com.example.i_rocket.RetrofitClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    public static String SPKU;
    private static Button next ;
    private Button prev;
    private int page;
    private View loading;
    private RelativeLayout rl_loading;
    private RelativeLayout rl_home_nav;
    private RelativeLayout rl_search_nav;
    private RelativeLayout rl_offline_nav;
    private RecyclerView rv_expedition;
    private TextView pageNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = RetrofitClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SPKU, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        page = sharedPreferences.getInt("page", 0);
        Call<ExpeditionResponse> call = apiService.getExpedition(10*page);
        Log.d("TAG0", "onCreate: " + call.request().url());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        ImageView iv_home = view.findViewById(R.id.IV_Home);
        ImageView iv_offline = view.findViewById(R.id.IV_offline);
        ImageView iv_search = view.findViewById(R.id.iv_search);
        next = view.findViewById(R.id.next_home);
        prev = view.findViewById(R.id.prev_home);
        pageNum = view.findViewById(R.id.page_num);

        @SuppressLint("DefaultLocale") String format = String.format("%02d", page);
        pageNum.setText(format);

        if(page == 0){
            prev.setVisibility(View.GONE);
        } else if (page == 150) {
            next.setVisibility(View.GONE);

        }

        rl_home_nav = view.findViewById(R.id.rl_home);
        rl_search_nav = view.findViewById(R.id.rl_search);
        rl_offline_nav = view.findViewById(R.id.rl_offline);

        rv_expedition = view.findViewById(R.id.rv_expedition_home);
        rv_expedition.setHasFixedSize(true);
        rv_expedition.setLayoutManager(new LinearLayoutManager(getContext()));

        loading = view.findViewById(R.id.ma_loading_home);
        rl_loading = view.findViewById(R.id.rl_loding_home);

        rl_home_nav.setBackgroundResource(R.drawable.press_view);
        showloding();
        tampilan(executor, call);

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
        next.setOnClickListener(v -> {
            int page = sharedPreferences.getInt("page",0);
            int page_new = page  + 1;
            editor.putInt("page", page_new);
            editor.apply();
            rl_home_nav.setBackgroundResource(R.drawable.press_view);
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });
        prev.setOnClickListener(v -> {
            int page = sharedPreferences.getInt("page",0);
            int page_new = page  - 1;
            editor.putInt("page", page_new);
            editor.apply();
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


    private void tampilan(ExecutorService executor, Call<ExpeditionResponse> call) {
        executor.execute(() -> {
            call.enqueue(new Callback<ExpeditionResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ExpeditionResponse> call, @NonNull Response<ExpeditionResponse> response) {
                    ExpeditionResponse expeditionResponse = response.body();
                    Log.d("TAG1", "onResponse: " + page);
                    if (response.isSuccessful() && expeditionResponse != null) {
                        for (Expedition ex : expeditionResponse.getResults()) {
                            
                            ExpeditionAdapter adapter = new ExpeditionAdapter(expeditionResponse.getResults());
                            loading.setVisibility(View.GONE);
                            rl_loading.setVisibility(View.GONE);
                            rv_expedition.setVisibility(View.VISIBLE);
                            rv_expedition.setAdapter(adapter);
                        }
                    } else {
                        Log.e("TAG", "onResponse: gagal " );
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExpeditionResponse> call, @NonNull Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.e("TAG", "Executor interrupted", e);
            }
        });
    }
    private void showloding(){
        loading.setVisibility(View.VISIBLE);
        rl_loading.setVisibility(View.VISIBLE);
        rv_expedition.setVisibility(View.GONE);
    }



}
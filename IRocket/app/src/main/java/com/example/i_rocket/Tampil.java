package com.example.i_rocket;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class Tampil {
    public static List<Expedition> results;
    public static List<Expedition> results_src;
    public static void tampilan(ExecutorService executor, Call<ExpeditionResponse> call, View loading, RelativeLayout rl_loading, RecyclerView rv_expedition, ImageView noCon, String str) {
        executor.execute(() -> {
            call.enqueue(new Callback<ExpeditionResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ExpeditionResponse> call, @NonNull Response<ExpeditionResponse> response) {
                    ExpeditionResponse expeditionResponse = response.body();
                    if (response.isSuccessful() && expeditionResponse != null ) {
                        ExpeditionAdapter adapter = new ExpeditionAdapter(expeditionResponse.getResults(), str);
                        Log.d("panjang", "onResponse: " + expeditionResponse.getResults().size());
                        if(expeditionResponse.getResults().isEmpty()) {
                            noCon.setImageResource(R.drawable.notfound);
                            noCon.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                        }else {
                            loading.setVisibility(View.GONE);
                            rl_loading.setVisibility(View.GONE);
                        }
                        if (rv_expedition != null){
                            String url = call.request().url().toString();
                            if (url.contains("search")){
                                Tampil.results_src = expeditionResponse.getResults();
                            }else {
                                Tampil.results = expeditionResponse.getResults();
                            }
                            rv_expedition.setVisibility(View.VISIBLE);
                            rv_expedition.setAdapter(adapter);
                        }

                    } else {
                        Log.e("TAG", "onResponse: gagal " );
                        noCon.setImageResource(R.drawable.erro500);
                        noCon.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExpeditionResponse> call, @NonNull Throwable t) {
                    noCon.setImageResource(R.drawable.nointernet);
                    noCon.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }

            });

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

    }
    public static void showloding(View loading, RelativeLayout rl_loading, RecyclerView rv_expedition, ImageView noCon){
        loading.setVisibility(View.VISIBLE);
        rl_loading.setVisibility(View.VISIBLE);
        noCon.setVisibility(View.GONE);
        if (rv_expedition != null){
            rv_expedition.setVisibility(View.GONE);
        }
    }
}

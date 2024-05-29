package com.example.i_rocket.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.i_rocket.ApiService;
import com.example.i_rocket.Expedition;
import com.example.i_rocket.ExpeditionResponse;
import com.example.i_rocket.R;
import com.example.i_rocket.RetrofitClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private Call<ExpeditionResponse> call;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    protected void onViewCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = RetrofitClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ExpeditionResponse> call = apiService.getExpedition(10);
        Log.d("TAG0", "onCreate: " + call.request().url());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        tampilan(executor, call);

    }
    private void tampilan(ExecutorService executor, Call<ExpeditionResponse> call) {
        executor.execute(() -> {
            call.enqueue(new Callback<ExpeditionResponse>() {
                @Override
                public void onResponse(@NonNull Call<ExpeditionResponse> call, @NonNull Response<ExpeditionResponse> response) {
                    ExpeditionResponse expeditionResponse = response.body();
                    Log.d("TAG1", "onResponse: " + response.isSuccessful());
                    if (response.isSuccessful() && expeditionResponse != null) {
                        for (Expedition ex : expeditionResponse.getResults()) {
                            Log.d("TAG", "onResponse: sukses " + ex.getName());
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.e("TAG", "Executor interrupted", e);
            }
        });
    }


}
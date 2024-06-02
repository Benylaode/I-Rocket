package com.example.i_rocket.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_rocket.ApiService;
import com.example.i_rocket.ExpeditionAdapter;
import com.example.i_rocket.ExpeditionResponse;
import com.example.i_rocket.R;
import com.example.i_rocket.RetrofitClient;
import com.example.i_rocket.Tampil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {
    public static String SPKU = "my";
    private View loading;
    private RelativeLayout rl_loading;
    private RelativeLayout rl_home_nav;
    private RelativeLayout rl_search_nav;
    private RelativeLayout rl_offline_nav;
    private RecyclerView rv_expedition;
    private String prompt;
    private EditText search;
    private ImageView noCon;
    private ExecutorService executor;
    private FragmentManager fragmentManager ;
    private String currentFragmentstr;


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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SPKU, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        prompt = sharedPreferences.getString("prompt", "");

        String[] tags = {"HOME", "SEARCH", "OFFLINE"};
        fragmentManager = getParentFragmentManager();
        List<Fragment> farment = fragmentManager.getFragments();
        for (String tag : tags){
            for(Fragment fragment : farment){
                if (fragmentManager.findFragmentByTag(tag) == fragment ){
                    currentFragmentstr = tag;
                    break;
                };
            }

        }
        rv_expedition = view.findViewById(R.id.rv_expedition_search);
        rv_expedition.setHasFixedSize(true);
        rv_expedition.setLayoutManager(new LinearLayoutManager(getContext()));

        loading = view.findViewById(R.id.ma_loading_search);
        rl_loading = view.findViewById(R.id.rl_loding_search);
        noCon = view.findViewById(R.id.ma_noint_search);

        prompt = sharedPreferences.getString("prompt", "");
        Call<ExpeditionResponse> call = apiService.getSearchEx(prompt);
        executor = Executors.newSingleThreadExecutor();

        if(!(Tampil.results_src == null)){
            ExpeditionAdapter adapter = new ExpeditionAdapter(Tampil.results_src, currentFragmentstr);
            rv_expedition.setAdapter(adapter);
        }
        ImageView iv_home = view.findViewById(R.id.IV_Home);
        ImageView iv_offline = view.findViewById(R.id.IV_offline);
        ImageView iv_search = view.findViewById(R.id.iv_search);

        rl_home_nav = view.findViewById(R.id.rl_home);
        rl_search_nav = view.findViewById(R.id.rl_search);
        rl_offline_nav = view.findViewById(R.id.rl_offline);
        search = view.findViewById(R.id.Search_fg);
        if(!(prompt.isEmpty())){
            search.setText(prompt);
            Tampil.showloding(loading, rl_loading, rv_expedition, noCon);
            Tampil.tampilan(executor, call, loading, rl_loading, rv_expedition, noCon, currentFragmentstr);
        }
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
            actionId == EditorInfo.IME_ACTION_DONE ||
            (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
            Tampil.results_src = null;
            String serc = search.getText().toString();
            editor.putString("prompt", serc);
            editor.apply();
            Tampil.showloding(loading, rl_loading, rv_expedition, noCon);
            Call<ExpeditionResponse> call1 = apiService.getSearchEx(serc);
            Tampil.tampilan(executor, call1, loading, rl_loading, rv_expedition, noCon, currentFragmentstr);

            return true;
        }   
            return false;
        });
        search.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            Tampil.results_src = null;
            String serc = search.getText().toString();
            editor.putString("prompt", serc);
            editor.apply();
            Tampil.showloding(loading, rl_loading, rv_expedition, noCon);
            Call<ExpeditionResponse> call1 = apiService.getSearchEx(serc);
            Tampil.tampilan(executor, call1, loading, rl_loading, rv_expedition, noCon, currentFragmentstr);
            return true;
            }
            return false;
        });

        rl_search_nav.setBackgroundResource(R.drawable.press_view);
        iv_offline.setOnClickListener(v -> {
            rl_offline_nav.setBackgroundResource(R.drawable.press_view);
            OfflineFragment offlineFragment = new OfflineFragment();
            fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, offlineFragment, "OFFLINE")
                    .addToBackStack(null)
                    .commit();
        });


        iv_search.setOnClickListener(v -> {
            rl_search_nav.setBackgroundResource(R.drawable.press_view);
            SearchFragment searchFragment = new SearchFragment();
            fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, searchFragment,  "SEARCH")
                    .addToBackStack(null)
                    .commit();
        });

        iv_home.setOnClickListener(v -> {
            rl_home_nav.setBackgroundResource(R.drawable.press_view);
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, homeFragment, "HOME")
                    .addToBackStack(null)
                    .commit();
        });
    }
}

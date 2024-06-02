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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_rocket.DataSource;
import com.example.i_rocket.Expedition;
import com.example.i_rocket.ExpeditionAdapter;
import com.example.i_rocket.R;
import com.example.i_rocket.RetrofitClient;

import java.util.List;


import retrofit2.Retrofit;

public class OfflineFragment extends Fragment  {
    private RelativeLayout rl_home_nav;
    private RelativeLayout rl_search_nav;
    private RelativeLayout rl_offline_nav;
    private RecyclerView rv_expedition;
    private String currentFragmentstr;
    private FragmentManager fragmentManager ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = RetrofitClient.getClient();

        ImageView iv_home = view.findViewById(R.id.IV_Home);
        ImageView iv_offline = view.findViewById(R.id.IV_offline);
        ImageView iv_search = view.findViewById(R.id.iv_search);
        ImageView not_found = view.findViewById(R.id.im_notfound);
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

        rl_home_nav = view.findViewById(R.id.rl_home);
        rl_search_nav = view.findViewById(R.id.rl_search);
        rl_offline_nav = view.findViewById(R.id.rl_offline);

        rl_offline_nav.setBackgroundResource(R.drawable.press_view);

        rv_expedition = view.findViewById(R.id.rv_expedition_off);
        rv_expedition.setHasFixedSize(true);
        rv_expedition.setLayoutManager(new LinearLayoutManager(getContext()));
        DataSource.getData(getContext());
        if(DataSource.expeditions_off.size() >= 1){
            ExpeditionAdapter adapter = new ExpeditionAdapter(DataSource.expeditions_off, currentFragmentstr );
            rv_expedition.setAdapter(adapter);
            not_found.setVisibility(View.GONE);
            rv_expedition.setVisibility(View.VISIBLE);
        }

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
    public void refresh() {
        DataSource.getData(getContext());
    }

}

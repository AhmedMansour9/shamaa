package com.shamaa.myapplication.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shamaa.myapplication.Adapter.Banner_Adapter;
import com.shamaa.myapplication.Adapter.Categories_Adapter;
import com.shamaa.myapplication.Adapter.Products_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Model.Banners;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.Banners_ViewModel;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.Products_ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }
    @BindView(R.id.recycler_Categroies)
    RecyclerView recycler_Categroies;
    @BindView(R.id.recycler_banner2)
    RecyclerView recycler_banner2;
    Banner_Adapter banner_adapter;
    View view;
    Categories_ViewModel categories_viewModel;
    Categories_Adapter categories_adapter;
    Banners_ViewModel banners_viewModel;
    String User_Token;
    List<Banners> listBanners=new ArrayList<>();
    final Handler handler = new Handler();
    int position = 0;
    Boolean end;
    final Runnable update = new Runnable() {
        public void run() {
            if(position == listBanners.size()-1){
                end = true;
            }
            else if (position == 0) {
                end = false;
            }
            if(!end){
                position++;
            } else {
                position--;
            }
//                vp_slider.setCurrentItem(page_position, true);
            recycler_banner2.smoothScrollToPosition(position);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        init();
        Get_Categories();
        Get_Banners();

        return view;
    }
    public void Get_Categories(){

        categories_viewModel = ViewModelProviders.of(this).get(Categories_ViewModel.class);
        categories_viewModel.getCetgroies(User_Token,"en",getContext()).observe(this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(@Nullable List<Categories> tripsData) {
                categories_adapter = new Categories_Adapter(tripsData,getActivity());
                recycler_Categroies.setAdapter(categories_adapter);
            }
        });
    }

    public void Get_Banners(){

        banners_viewModel = ViewModelProviders.of(this).get(Banners_ViewModel.class);
        banners_viewModel.getBanners(User_Token,"en",getContext()).observe(this, new Observer<List<Banners>>() {
            @Override
            public void onChanged(@Nullable List<Banners> tripsData) {
                listBanners=tripsData;
                banner_adapter = new Banner_Adapter(tripsData,getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recycler_banner2.setLayoutManager(linearLayoutManager);
                recycler_banner2.setAdapter(banner_adapter);
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(update);
                    }
                }, 2000, 2000);

            }
        });
    }

    public void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_Categroies.setLayoutManager(linearLayoutManager);
        recycler_Categroies.setItemAnimator(new DefaultItemAnimator());
        recycler_Categroies.setLayoutManager(linearLayoutManager);
//        categories_adapter.setOnClicklistner(this);
        recycler_Categroies.setAdapter(categories_adapter);
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();

    }
}

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.shamaa.myapplication.Adapter.Banner_Adapter;
import com.shamaa.myapplication.Adapter.Categories_Adapter;
import com.shamaa.myapplication.Adapter.Diamod_Adapter;
import com.shamaa.myapplication.Adapter.Favourit_Adapter;
import com.shamaa.myapplication.Adapter.Products_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Model.AddToFavourit;
import com.shamaa.myapplication.Model.Banners;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View.DetailsProduct_id;
import com.shamaa.myapplication.View.SubCategoryid_View;
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
public class Home extends Fragment implements DetailsProduct_id,SubCategoryid_View , SwipeRefreshLayout.OnRefreshListener{


    public Home() {
        // Required empty public constructor
    }
    @BindView(R.id.recycler_Categroies)
    RecyclerView recycler_Categroies;
    @BindView(R.id.recycler_banner2)
    RecyclerView recycler_banner2;
    Banner_Adapter banner_adapter;
    @BindView(R.id.Scroll_Home)
    ScrollView Scroll_Home;
    @BindView(R.id.swipe_Banners)
    SwipeRefreshLayout swipe_Banners;
    @BindView(R.id.progross_home)
    GifImageView progross;
    @BindView(R.id.recycler_BestDiamond)
    RecyclerView recyclerProducts;
    Diamod_Adapter products_Adapter;
    Products_ViewModel tripsViewModel;

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
        SwipRefresh();
        return view;
    }
    public void Get_Categories(){
        categories_viewModel = ViewModelProviders.of(this).get(Categories_ViewModel.class);
        categories_viewModel.getCetgroies(User_Token,"en",getContext()).observe(this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(@Nullable List<Categories> tripsData) {
                if(tripsData!=null) {
                    categories_adapter = new Categories_Adapter(tripsData, getActivity());
                    categories_adapter.setOnClicklistner(Home.this);
                    recycler_Categroies.setAdapter(categories_adapter);
                }
            }
        });
    }
    public void SwipRefresh(){
        swipe_Banners.setOnRefreshListener(this);
        swipe_Banners.setEnabled(true);
        swipe_Banners.setRefreshing(true);
        swipe_Banners.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipe_Banners.post(new Runnable() {
            @Override
            public void run() {
                Get_Categories();
                Get_Banners();
                Get_products();

            }
        });
    }
    public void Get_products(){
        Scroll_Home.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);
        tripsViewModel = ViewModelProviders.of(this).get(Products_ViewModel.class);
        tripsViewModel.getFavourit(User_Token,"en",getContext()).observe(this, new Observer<List<Products_Model>>() {
            @Override
            public void onChanged(@Nullable List<Products_Model> tripsData) {
                progross.setVisibility(View.GONE);
                Scroll_Home.setAlpha(1f);
                swipe_Banners.setRefreshing(false);
                if(tripsData!=null) {
                    products_Adapter = new Diamod_Adapter(tripsData, getActivity());
                    products_Adapter.setOnClicklistner(Home.this);
                    recyclerProducts.setAdapter(products_Adapter);

                }
            }
        });
    }
    public void Get_Banners(){
        Scroll_Home.setAlpha(.3f);
        progross.setVisibility(View.VISIBLE);
        banners_viewModel = ViewModelProviders.of(this).get(Banners_ViewModel.class);
        banners_viewModel.getBanners(User_Token,"en",getContext()).observe(this, new Observer<List<Banners>>() {
            @Override
            public void onChanged(@Nullable List<Banners> tripsData) {
                if (tripsData != null) {
                    listBanners = tripsData;
                    Scroll_Home.setAlpha(1);
                    progross.setVisibility(View.GONE);
                    banner_adapter = new Banner_Adapter(tripsData, getActivity());
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
                    }, 3000, 3000);

                }
            }
        });
    }

    public void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_Categroies.setLayoutManager(linearLayoutManager);
        recycler_Categroies.setItemAnimator(new DefaultItemAnimator());
        recycler_Categroies.setLayoutManager(linearLayoutManager);

        recyclerProducts.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerProducts.setAdapter(products_Adapter);

        recycler_Categroies.setAdapter(categories_adapter);
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();

    }

    @Override
    public void id(String id) {
        SuberCategories detailsHomeProductFragment=new SuberCategories();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        detailsHomeProductFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.Rela_Home,detailsHomeProductFragment)
                .addToBackStack(null).commit();

    }

    @Override
    public void onRefresh() {
        swipe_Banners.setRefreshing(false);
        Get_Categories();
        Get_Banners();
        Get_products();

    }

    @Override
    public void Details(Products_Model products_model) {
        Details_Product detailsHomeProductFragment=new Details_Product();
        Bundle bundle=new Bundle();
        bundle.putSerializable("details",products_model);
        detailsHomeProductFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.Rela_Home,detailsHomeProductFragment)
                .addToBackStack(null).commit();

    }

    @Override
    public void AddToFavourit(String Productid) {
        tripsViewModel = ViewModelProviders.of(this).get(Products_ViewModel.class);
        tripsViewModel.AddToFavourit(User_Token,Productid,"en",getContext()).observe(Home.this, new Observer<AddToFavourit>() {
            @Override
            public void onChanged(@Nullable AddToFavourit tripsData) {
                if(tripsData!=null) {


                }
            }
        });

    }
}

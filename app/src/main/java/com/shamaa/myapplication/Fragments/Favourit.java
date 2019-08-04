package com.shamaa.myapplication.Fragments;


import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamaa.myapplication.Adapter.Favourit_Adapter;
import com.shamaa.myapplication.Adapter.Products_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.Model.AddToFavourit;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View.DetailsProduct_id;
import com.shamaa.myapplication.View_Model.Products_ViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourit extends Fragment implements DetailsProduct_id,SwipeRefreshLayout.OnRefreshListener{


    public Favourit() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_Products)
    RecyclerView recyclerProducts;
    Favourit_Adapter products_Adapter;
    List<Products_Model> tripsData;
    View view;
    Products_ViewModel tripsViewModel;
    @BindView(R.id.ReLative_Products)
    RelativeLayout ReLa_Products;
    @BindView(R.id.progross)
    GifImageView progross;
    @BindView(R.id.swipe_ProductsFavourit)
    SwipeRefreshLayout swipe_Products;
    String Id,UserToken;
    @BindView(R.id.img_cart)
    ImageView img_cart;
    @BindView(R.id.nocart)
    TextView nocart;
    String Lang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favourit, container, false);
        ButterKnife.bind(this,view);
        UserToken= SharedPrefManager.getInstance(getContext()).getUserToken();
        Language();
        init();

        SwipRefresh();

        return view;
    }

    public void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerProducts.setLayoutManager(gridLayoutManager);
        recyclerProducts.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerProducts.setAdapter(products_Adapter);

    }
    public void Get_products(){
        ReLa_Products.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);
        tripsViewModel = ViewModelProviders.of(this).get(Products_ViewModel.class);
        tripsViewModel.getFavourit(UserToken,Lang,getContext()).observe(this, new Observer<List<Products_Model>>() {
            @Override
            public void onChanged(@Nullable List<Products_Model> tripsData) {
                progross.setVisibility(View.GONE);
                swipe_Products.setRefreshing(false);
                ReLa_Products.setAlpha(1f);
                if(tripsData!=null) {
                    products_Adapter = new Favourit_Adapter(tripsData, getActivity());
                    products_Adapter.setOnClicklistner(Favourit.this);
                    recyclerProducts.setAdapter(products_Adapter);
                    img_cart.setVisibility(View.GONE);
                    nocart.setVisibility(View.GONE);

                }else {
                    img_cart.setVisibility(View.VISIBLE);
                    nocart.setVisibility(View.VISIBLE);

                }

            }
        });
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    public void SwipRefresh(){
        swipe_Products.setOnRefreshListener(this);
        swipe_Products.setEnabled(true);
        swipe_Products.setRefreshing(true);
        swipe_Products.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipe_Products.post(new Runnable() {
            @Override
            public void run() {
                Get_products();
            }
        });
    }


    @Override
    public void Details(Products_Model products_model) {
        Details_Product detailsHomeProductFragment=new Details_Product();
        Bundle bundle=new Bundle();
        bundle.putSerializable("details",products_model);
        detailsHomeProductFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.Rela_Favourit,detailsHomeProductFragment)
                .addToBackStack(null).commit();

    }

    @Override
    public void AddToFavourit(String Productid) {
        tripsViewModel = ViewModelProviders.of(this).get(Products_ViewModel.class);
        tripsViewModel.AddToFavourit(UserToken,Productid,Lang,getContext()).observe(Favourit.this, new Observer<AddToFavourit>() {
            @Override
            public void onChanged(@Nullable AddToFavourit tripsData) {
                if(tripsData!=null) {

                 Get_products();
                }else {

                }
            }
        });
    }

    @Override
    public void onRefresh() {
        swipe_Products.setRefreshing(true);
        Get_products();

    }
    public void Language(){
        if(Language.isRTL()){
            Lang="he";
        }else {
            Lang="en";
        }

    }
}


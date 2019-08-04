package com.shamaa.myapplication.Fragments;


import android.content.res.Resources;
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

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.Cart_Adapter;
import com.shamaa.myapplication.Adapter.Favourit_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.Model.AddToFavourit;
import com.shamaa.myapplication.Model.CartDetails;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.Model.UpdateCart_Response;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View.Count_View;
import com.shamaa.myapplication.View.DetailsProduct_id;
import com.shamaa.myapplication.View_Model.Cart_ViewModel;
import com.shamaa.myapplication.View_Model.Products_ViewModel;
import com.shamaa.myapplication.View_Model.UpdateCart_ViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment implements Count_View,DetailsProduct_id,SwipeRefreshLayout.OnRefreshListener{


    public Cart() {
        // Required empty public constructor
    }
    @BindView(R.id.recycler_Cart)
    RecyclerView recyclerProducts;
    Cart_Adapter products_Adapter;
    List<Products_Model> tripsData;
    @BindView(R.id.Total_Price)
    TextView Total_Price;
    View view;
    Cart_ViewModel tripsViewModel;
    @BindView(R.id.ReLative_Products)
    RelativeLayout ReLa_Products;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.progross)
    GifImageView progross;
    @BindView(R.id.swipe_Products)
    SwipeRefreshLayout swipe_Products;
    String Id,UserToken;
    double total_price;
    double res = 0;
    @BindView(R.id.img_cart)
    ImageView  img_cart;
    @BindView(R.id.nocart)
    TextView nocart;
    @BindView(R.id.Btn_Checkout)
    Button Btn_Checkout;
    String Lang;
    UpdateCart_ViewModel updateCart_viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this,view);
        updateCart_viewModel = ViewModelProviders.of(this).get(UpdateCart_ViewModel.class);
        Language();
        UserToken= SharedPrefManager.getInstance(getContext()).getUserToken();
        init();

        SwipRefresh();

        Btn_Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check_Order detailsHomeProductFragment=new Check_Order();
                Bundle bundle=new Bundle();
                bundle.putString("price",String.valueOf(res));
                detailsHomeProductFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.Rela_Cart,detailsHomeProductFragment)
                        .addToBackStack(null).commit();

            }
        });

        return view;
    }

    public void init(){
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        recyclerProducts.setLayoutManager(gridLayoutManager);
        recyclerProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerProducts.setAdapter(products_Adapter);

    }
    public void Get_products(){
        ReLa_Products.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);
        tripsViewModel = ViewModelProviders.of(this).get(Cart_ViewModel.class);

        tripsViewModel.getCart(UserToken,Lang,getContext()).observe(this, new Observer<List<CartDetails>>() {
            @Override
            public void onChanged(@Nullable List<CartDetails> tripsData) {
                progross.setVisibility(View.GONE);
                ReLa_Products.setAlpha(1f);

                if(tripsData!=null) {
                    for (int i = 0; i < tripsData.size(); i++) {
                        total_price = Double.parseDouble(tripsData.get(i).getTotalPrice());
                        res += total_price;
                    }
                    recyclerProducts.setVisibility(View.VISIBLE);
                    Total_Price.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    Total_Price.setText(String.valueOf(res)+" "+getActivity().getResources().getString(R.string.currency));
                    products_Adapter = new Cart_Adapter(tripsData, getActivity());
                    products_Adapter.count(Cart.this);
                    recyclerProducts.setAdapter(products_Adapter);
                    swipe_Products.setRefreshing(false);
                    img_cart.setVisibility(View.GONE);
                    Btn_Checkout.setVisibility(View.VISIBLE);
                    nocart.setVisibility(View.GONE);
                }else {
                    Total_Price.setVisibility(View.GONE);
                    Btn_Checkout.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    img_cart.setVisibility(View.VISIBLE);
                    nocart.setVisibility(View.VISIBLE);
                    recyclerProducts.setVisibility(View.GONE);
                    TabLayout.Tab tab = TabsLayouts.tabLayout.getTabAt(2); // fourth tab
                    View tabView = tab.getCustomView();
                    TextView textView = tabView.findViewById(R.id.cartt);
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        swipe_Products.setRefreshing(false);
        Get_products();
    }

    public void SwipRefresh(){
        swipe_Products.setOnRefreshListener(this);

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
    }

    @Override
    public void AddToFavourit(String Productid) {

    }


    @Override
    public void count_plus(String id) {

        updateCart_viewModel.updateCart(id,UserToken,"en",getContext()).observe(this, new Observer<UpdateCart_Response>() {
            @Override
            public void onChanged(@Nullable UpdateCart_Response tripsData) {


                Get_products();

            }

        });
    }

    @Override
    public void count_minus(String id) {

        updateCart_viewModel.updateminusCart(id,UserToken,"en",getContext()).observe(this, new Observer<UpdateCart_Response>() {
            @Override
            public void onChanged(@Nullable UpdateCart_Response tripsData) {
                Get_products();
            }

        });

    }

    @Override
    public void count_delete(String id) {
        updateCart_viewModel.deleteCart(id,UserToken,"en",getContext()).observe(this, new Observer<UpdateCart_Response>() {
            @Override
            public void onChanged(@Nullable UpdateCart_Response tripsData) {
                Get_products();
            }

        });
    }
    public void Language(){
        if(Language.isRTL()){
            Lang="he";
        }else {
            Lang="en";
        }

    }
}

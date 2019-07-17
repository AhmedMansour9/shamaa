package com.shamaa.myapplication.Fragments;


import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shamaa.myapplication.Adapter.Categories_Adapter;
import com.shamaa.myapplication.Adapter.Products_Adapter;
import com.shamaa.myapplication.Adapter.SubCategories_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.Products_ViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuberCategories extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    public SuberCategories() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_SubCatehories)
    RecyclerView recycler_SubCatehories;
    SubCategories_Adapter subCategories_adapter;
    List<Categories> tripsData;
    View view;
    Categories_ViewModel categories_viewModel;
    @BindView(R.id.ReLative_Products)
    RelativeLayout ReLa_Products;
    @BindView(R.id.progross)
    GifImageView progross;
    @BindView(R.id.swipe_SubCategories)
    SwipeRefreshLayout swipeRefreshLayout;
    String User_Token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_suber_categories, container, false);
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();
        init();

        SwipRefresh();


        return view;
    }
    public void Get_SubCategories(){
        ReLa_Products.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);
        categories_viewModel = ViewModelProviders.of(this).get(Categories_ViewModel.class);
        categories_viewModel.getSubCetgroies(User_Token,"en",getContext(),"").observe(this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(@Nullable List<Categories> tripsData) {
                subCategories_adapter = new SubCategories_Adapter(tripsData,getActivity());
                progross.setVisibility(View.GONE);
                ReLa_Products.setAlpha(1f);
                recycler_SubCatehories.setAdapter(subCategories_adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_SubCatehories.setLayoutManager(gridLayoutManager);
        recycler_SubCatehories.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler_SubCatehories.setItemAnimator(new DefaultItemAnimator());

    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        Get_SubCategories();

    }

    public void SwipRefresh(){
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Get_SubCategories();
            }
        });
    }

}

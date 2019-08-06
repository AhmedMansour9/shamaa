package com.shamaa.myapplication.Fragments;


import android.content.Context;
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
import android.widget.RelativeLayout;

import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.MyOrders_Adapter;
import com.shamaa.myapplication.Adapter.SubCategories_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.MyOrders;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View.OrderId;
import com.shamaa.myapplication.View.SubCategoryid_View;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.Orders_ViewModel;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders_Fragment extends Fragment implements OrderId,SwipeRefreshLayout.OnRefreshListener{


    public MyOrders_Fragment() {
        // Required empty public constructor
    }
    String Lang;
    @BindView(R.id.recycler_SubCatehories)
    RecyclerView recycler_SubCatehories;
    MyOrders_Adapter myOrders_adapter;
    View view;
    Orders_ViewModel orders_viewModel;
    @BindView(R.id.ReLative_SubCategories)
    RelativeLayout ReLative_SubCategories;
    @BindView(R.id.progrossSubCategory)
    GifImageView progross;
    @BindView(R.id.swipe_SubCategories)
    SwipeRefreshLayout swipeRefreshLayout;
    String User_Token,Id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_orders_, container, false);
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();
        ButterKnife.bind(this,view);
        Language();
        init();

        SwipRefresh();



        return view;
    }
    public void Get_Orders(){
        ReLative_SubCategories.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);
        orders_viewModel = ViewModelProviders.of(this).get(Orders_ViewModel.class);
        orders_viewModel.getOrders(User_Token,Lang,getContext()).observe(this, new Observer<List<MyOrders>>() {
            @Override
            public void onChanged(@Nullable List<MyOrders> tripsData) {
                if(tripsData!=null) {
                    Collections.reverse(tripsData);
                    myOrders_adapter = new MyOrders_Adapter(tripsData, getActivity());
                    myOrders_adapter.setOnClicklistner(MyOrders_Fragment.this);
                    progross.setVisibility(View.GONE);
                    ReLative_SubCategories.setAlpha(1f);
                    recycler_SubCatehories.setAdapter(myOrders_adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
    public void init(){
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler_SubCatehories.setLayoutManager(gridLayoutManager);
        recycler_SubCatehories.setItemAnimator(new DefaultItemAnimator());

    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        Get_Orders();

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
                Get_Orders();
            }
        });
    }

//    @Override
//    public void id(String id) {
//
//    }

    public void Language(){
        if(Language.isRTL()){
            Lang="he";
        }else {
            Lang="en";
        }

    }

    @Override
    public void id(String id) {
        OrderDetails detailsHomeProductFragment=new OrderDetails();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        detailsHomeProductFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.ReLa_Profile,detailsHomeProductFragment)
                .addToBackStack(null).commit();

    }
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            TabsLayouts.Visablty = false;
        } else {

        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TabsLayouts.Visablty = false;
    }
}

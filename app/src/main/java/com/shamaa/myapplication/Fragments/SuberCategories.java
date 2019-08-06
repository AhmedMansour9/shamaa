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
import android.widget.TextView;

import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.Categories_Adapter;
import com.shamaa.myapplication.Adapter.Products_Adapter;
import com.shamaa.myapplication.Adapter.SubCategories_Adapter;
import com.shamaa.myapplication.GridSpacingItemDecoration;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View.SubCategoryid_View;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.Products_ViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuberCategories extends Fragment implements SubCategoryid_View,SwipeRefreshLayout.OnRefreshListener{


    public SuberCategories() {
        // Required empty public constructor
    }
   String Lang;
    @BindView(R.id.recycler_SubCatehories)
    RecyclerView recycler_SubCatehories;
    SubCategories_Adapter subCategories_adapter;
    View view;
    Categories_ViewModel categories_viewModel;
    @BindView(R.id.ReLative_SubCategories)
    RelativeLayout ReLative_SubCategories;
    @BindView(R.id.progrossSubCategory)
    GifImageView progross;
    @BindView(R.id.swipe_SubCategories)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.T_CategoryName)
    TextView T_CategoryName;
    String User_Token,Id,Name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_suber_categories, container, false);
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();
        ButterKnife.bind(this,view);
        Language();
        init();

        SwipRefresh();


        return view;
    }
    public void Get_SubCategories(){
        ReLative_SubCategories.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);

        categories_viewModel.getSubCetgroies(User_Token,Lang,getContext(),Id).observe(this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(@Nullable List<Categories> tripsData) {
                progross.setVisibility(View.GONE);
                ReLative_SubCategories.setAlpha(1f);
                swipeRefreshLayout.setRefreshing(false);
                if(tripsData!=null) {
                    subCategories_adapter = new SubCategories_Adapter(tripsData, getActivity());
                    subCategories_adapter.setOnClicklistner(SuberCategories.this);
                    recycler_SubCatehories.setAdapter(subCategories_adapter);

                }
            }
        });
    }
    public void init(){
        categories_viewModel = ViewModelProviders.of(this).get(Categories_ViewModel.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_SubCatehories.setLayoutManager(gridLayoutManager);
        recycler_SubCatehories.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler_SubCatehories.setItemAnimator(new DefaultItemAnimator());
        Bundle b=getArguments();
        Id=b.getString("id");
        Name=b.getString("name");
        T_CategoryName.setText(Name);
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

    @Override
    public void id(String id,String name) {
        Products detailsHomeProductFragment=new Products();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        bundle.putBoolean("BOOLEAN_VALUE",false);
        detailsHomeProductFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.Rela_Home,detailsHomeProductFragment).addToBackStack(null).commit();

    }

    public void Language(){
        if(Language.isRTL()){
            Lang="he";
        }else {
            Lang="en";
        }
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

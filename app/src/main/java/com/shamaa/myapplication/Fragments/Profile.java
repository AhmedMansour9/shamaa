package com.shamaa.myapplication.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamaa.myapplication.Activities.MainActivity;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.SubCategories_Adapter;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Profile_Details;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.Profile_ViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }
    @BindView(R.id.T_MyOrders)
    TextView T_MyOrders;
    @BindView(R.id.T_Aboutus)
    TextView T_Aboutus;
    @BindView(R.id.T_Contactus)
    TextView T_Contactus;
    @BindView(R.id.T_Setting)
    TextView T_Setting;
    @BindView(R.id.T_Logout)
    TextView T_Logout;
    @BindView(R.id.Rela_Logout)
    RelativeLayout Rela_Logout;
    @BindView(R.id.T_ProfileName)
    TextView T_ProfileName;
    @BindView(R.id.T_ProfileEmail)
    TextView T_ProfileEmail;
    @BindView(R.id.T_ProfilePhone)
    TextView T_ProfilePhone;
    @BindView(R.id.T_MyProfileOrders)
    TextView T_MyProfileOrders;
    @BindView(R.id.T_MyProfileProducts)
    TextView T_MyProfileProducts;
    @BindView(R.id.T_MyProfilePayMent)
    TextView T_MyProfilePayMent;
    @BindView(R.id.T_Edit)
    TextView T_Edit;
    @BindView(R.id.Rela_Contactus)
    RelativeLayout Rela_Contactus;
    @BindView(R.id.Rela_MyOrders)
    RelativeLayout Rela_MyOrders;
    Profile_ViewModel profile_viewModel;
    View view;
    String User_Token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        User_Token=SharedPrefManager.getInstance(getContext()).getUserToken();
         ChangeFont();
         Get_Profle();
        Rela_MyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.ReLa_Profile, new MyOrders_Fragment()).addToBackStack(null).commit();

            }
        });
        T_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.ReLa_Profile, new Edit_Profile()).addToBackStack(null).commit();

            }
        });
        Rela_Contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.ReLa_Profile, new Contact_Us()).addToBackStack(null).commit();

            }
        });
        Rela_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getContext()).saveUserToken(null);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

            }
        });
        return view;
    }

    public void ChangeFont(){
        Typeface customFontBold= Typeface.createFromAsset( getActivity().getAssets(), "nun.ttf" );
        T_MyOrders.setTypeface( customFontBold );
        T_Aboutus.setTypeface( customFontBold );
        T_Contactus.setTypeface( customFontBold );
        T_Setting.setTypeface( customFontBold );
        T_Logout.setTypeface( customFontBold );
        T_ProfileName.setTypeface( customFontBold );
        T_ProfileEmail.setTypeface( customFontBold );
        T_ProfilePhone.setTypeface( customFontBold );
        T_MyProfileOrders.setTypeface( customFontBold );
        T_MyProfileProducts.setTypeface( customFontBold );
        T_MyProfilePayMent.setTypeface( customFontBold );
        T_Edit.setTypeface( customFontBold );

    }

    public void getInformation(){


    }

    public void Get_Profle(){
        profile_viewModel = ViewModelProviders.of(this).get(Profile_ViewModel.class);
        profile_viewModel.getProfile(User_Token,"en",getContext()).observe(this, new Observer<List<Profile_Details>>() {
            @Override
            public void onChanged(@Nullable List<Profile_Details> tripsData) {
                if(tripsData!=null) {
                    T_MyProfileOrders.setText(tripsData.get(0).getTotalOrder() + " " + getActivity().getResources().getString(R.string.orders));
                    T_MyProfileProducts.setText(tripsData.get(0).getTotalProduct() + " " + getActivity().getResources().getString(R.string.products));
                    T_MyProfilePayMent.setText(tripsData.get(0).getPaid() + " " + "$");
                    T_ProfileName.setText(tripsData.get(0).getName() );
                    T_ProfileEmail.setText(tripsData.get(0).getEmail());
                    T_ProfilePhone.setText(tripsData.get(0).getPhone());


                }
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            TabsLayouts.Visablty = true;
        } else {

        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TabsLayouts.Visablty = true;
    }
}

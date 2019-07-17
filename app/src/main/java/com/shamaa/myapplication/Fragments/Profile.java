package com.shamaa.myapplication.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamaa.myapplication.R;

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

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
         ChangeFont();
        T_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.ReLa_Profile, new Filtertion()).commit();

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
}

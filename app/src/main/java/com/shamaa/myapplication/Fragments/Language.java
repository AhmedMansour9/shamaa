package com.shamaa.myapplication.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.R;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Language extends Fragment {


    public Language() {
        // Required empty public constructor
    }
    RelativeLayout btn_Arabic,Btn_English;
    SharedPreferences.Editor share;
     View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_language, container, false);
        btn_Arabic=view.findViewById(R.id.Rela_Arabic);
        Btn_English=view.findViewById(R.id.Rela_English);
       Lan_Arabic();
       Lan_English();

        return view;
    }
    public void Lan_Arabic(){
        btn_Arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share=getActivity().getSharedPreferences("Language",MODE_PRIVATE).edit();
                share.putString("Lann","he");
                share.commit();
                startActivity(new Intent(getContext(), TabsLayouts.class));
                getActivity().finish();


            }
        });
    }
    public void Lan_English(){
        Btn_English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share=getActivity().getSharedPreferences("Language",MODE_PRIVATE).edit();
                share.putString("Lann","en");
                share.commit();
                startActivity(new Intent(getContext(), TabsLayouts.class));
                getActivity().finish();


            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TabsLayouts.Visablty = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TabsLayouts.Visablty = true;
    }


}

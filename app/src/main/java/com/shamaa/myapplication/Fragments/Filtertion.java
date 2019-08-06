package com.shamaa.myapplication.Fragments;


import android.content.Context;
import android.graphics.Color;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.Categories_Adapter;
import com.shamaa.myapplication.Adapter.ClaiberFiltertion_Adapter;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.ClaiberFiltertion;
import com.shamaa.myapplication.Model.Style_Details;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.Filter_Style;
import com.shamaa.myapplication.View_Model.StylesFiltertion_ViewModel;
import com.shamaa.myapplication.View_Model.Stylestbyid_ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Filtertion extends Fragment {


    public Filtertion() {
        // Required empty public constructor
    }
    View view;
    @BindView(R.id.relative_man)
    RelativeLayout relativeMan;
    @BindView(R.id.relative_man_selected)
    RelativeLayout relativeManSelected;
    @BindView(R.id.relative_woman)
    RelativeLayout relativeWoman;
    @BindView(R.id.relative_woman_selected)
    RelativeLayout relativeWomanSelected;
    @BindView(R.id.recycler_Stylest)
    RecyclerView recycler_Stylest;
    String Lang;
    @BindView(R.id.Btn_Filtertion)
    Button Btn_Filtertion;
    StylesFiltertion_ViewModel stylesFiltertion_viewModel;
    String UserType;
    @BindView(R.id.Style_Spinner)
    Spinner Style_Spinner;
    StylesFiltertion_ViewModel Typeid_viewModel;
    String User_Token,Type,Type_id,Style,Style_id;
    ArrayAdapter<Style_Details> listStylest;
    ClaiberFiltertion_Adapter claiberFiltertion_adapter;
    TextView tvMin,tvMax;
    List<Filter_Style> claiberate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_filtertion, container, false);
        ButterKnife.bind(this,view);
        claiberate=new ArrayList<>();
        claiberFiltertion_adapter = new ClaiberFiltertion_Adapter(claiberate, getActivity());

        Language();
          init();
         initPrices();
         ClickMan();
         ClickManSelected();
         ClickWoman();
         ClickWomanSelected();
         GetClaiber();
         Get_Style();
        Btn_Filtertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(claiberFiltertion_adapter.getStyleId()!=null) {
                    Style_id = claiberFiltertion_adapter.getStyleId();
                }
                getActivity().getSupportFragmentManager().popBackStack();

                Products detailsHomeProductFragment=new Products();
                Bundle bundle=new Bundle();
                bundle.putBoolean("BOOLEAN_VALUE",true);
                bundle.putString("man",UserType);
                bundle.putString("style_id",Style_id);
                bundle.putString("type_cliber",Type_id);
                bundle.putString("minprice",tvMin.getText().toString());
                bundle.putString("maxprice",tvMax.getText().toString());
                detailsHomeProductFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.Rela_Home,detailsHomeProductFragment).addToBackStack(null).commitAllowingStateLoss();
            }
        });

        return view;
    }
    public void Get_Style(){
        stylesFiltertion_viewModel = ViewModelProviders.of(this).get(StylesFiltertion_ViewModel.class);
        stylesFiltertion_viewModel.getStyles(User_Token,Lang,getContext()).observe(this, new Observer<List<ClaiberFiltertion>>() {
            @Override
            public void onChanged(@Nullable List<ClaiberFiltertion> tripsData) {
                if(tripsData!=null) {
                    for(int i=0;i<tripsData.size();i++){
                        Filter_Style filter_style=new Filter_Style();
                        filter_style.setStyleId(tripsData.get(i).getStyleId());
                        filter_style.setStyleImage(tripsData.get(i).getStyleImage());
                        filter_style.setStyleName(tripsData.get(i).getStyleName());
                        claiberate.add(filter_style);
                    }
                    claiberFiltertion_adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void init(){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),2);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_Stylest.setItemAnimator(new DefaultItemAnimator());
        recycler_Stylest.setLayoutManager(linearLayoutManager);

        recycler_Stylest.setAdapter(claiberFiltertion_adapter);


    }
    public void initPrices(){
        relativeManSelected.setVisibility(View.VISIBLE);
        relativeMan.setVisibility(View.INVISIBLE);
        UserType="1";
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();
          tvMin = (TextView) view.findViewById(R.id.textMin1);
          tvMax = (TextView) view.findViewById(R.id.textMax1);
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar5);

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
            }
        });

        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
    }

    public void ClickMan(){
        relativeMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeManSelected.setVisibility(View.VISIBLE);
                relativeMan.setVisibility(View.INVISIBLE);
                relativeWomanSelected.setVisibility(View.INVISIBLE);
                relativeWoman.setVisibility(View.VISIBLE);
                UserType="1";
            }
        });

    }
    public void ClickManSelected(){
        relativeManSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeManSelected.setVisibility(View.INVISIBLE);
                relativeMan.setVisibility(View.VISIBLE);
                relativeWomanSelected.setVisibility(View.VISIBLE);
                relativeWoman.setVisibility(View.INVISIBLE);
                UserType="2";
            }
        });

    }
    public void ClickWoman(){
        relativeWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeWomanSelected.setVisibility(View.VISIBLE);
                relativeWoman.setVisibility(View.INVISIBLE);
                relativeManSelected.setVisibility(View.INVISIBLE);
                relativeMan.setVisibility(View.VISIBLE);
                UserType="2";
            }
        });

    }
    public void ClickWomanSelected(){
        relativeWomanSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeWomanSelected.setVisibility(View.INVISIBLE);
                relativeWoman.setVisibility(View.VISIBLE);
                relativeManSelected.setVisibility(View.VISIBLE);
                relativeMan.setVisibility(View.INVISIBLE);
                UserType="1";
            }
        });

    }

    public void GetClaiber(){
        try {
        Typeid_viewModel = ViewModelProviders.of(this).get(StylesFiltertion_ViewModel.class);
        Typeid_viewModel.getType(User_Token,Lang,getContext()).observe(this, new Observer<List<Style_Details>>() {
            @Override
            public void onChanged(@Nullable List<Style_Details> tripsData) {
                if(tripsData!=null) {
                    listStylest = new ArrayAdapter<Style_Details>(getActivity(), R.layout.textcolorspinner, tripsData) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                            textView.setTextColor(Color.WHITE);
                            return textView;
                        }
                    };
                    listStylest.setDropDownViewResource(R.layout.spinner_dropdown_item);

                    Style_Spinner.setPrompt(getResources().getString(R.string.selectcaliber));

                    Style_Spinner.setAdapter(listStylest);

                        Style_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                Type = Style_Spinner.getSelectedItem().toString();

                                for (i = 0; i < tripsData.size(); i++) {
                                    if (tripsData.get(i).getName().equals(Type)) {
                                        Type_id = String.valueOf(tripsData.get(i).getId());
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });



                }else {
                    Style_Spinner.setVisibility(View.GONE);
                }


            }
        });
        }catch (Exception e){

        }

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

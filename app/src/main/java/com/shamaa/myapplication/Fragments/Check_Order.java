package com.shamaa.myapplication.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Model.EditProfile;
import com.shamaa.myapplication.Model.Order_Response;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.EditProfile_ViewModel;
import com.shamaa.myapplication.View_Model.Order_ViewModel;

import static com.shamaa.myapplication.Fragments.Login.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Check_Order extends Fragment {


    public Check_Order() {
        // Required empty public constructor
    }
    @BindView(R.id.checkouut)
    Button orderBtn;
    @BindView(R.id.E_Address)
    EditText E_Address;
    @BindView(R.id.E_City)
    EditText E_City;
    @BindView(R.id.E_Phone)
    EditText E_Phone;
    Order_ViewModel order_viewModel;
    String payment;
    View view;
    String Price,User_token;
    @BindView(R.id.Rela_Login)
    RelativeLayout Rela_Login;
    @BindView(R.id.Total_Price)
    TextView Total_Price;
    @BindView(R.id.progross)
    GifImageView progross;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_check__order, container, false);
        order_viewModel = ViewModelProviders.of(this).get(Order_ViewModel.class);
        ButterKnife.bind(this,view);
        User_token= SharedPrefManager.getInstance(getContext()).getUserToken();
       Bundle bundle=getArguments();
       if(bundle!=null){
           Price=bundle.getString("price");
           Total_Price.setText(Price);
       }
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FUtilsValidation.isEmpty(E_Address, "");
                        FUtilsValidation.isEmpty(E_City, "");
                        FUtilsValidation.isEmpty(E_Phone, "");
//                        if(radioGroup.getCheckedRadioButtonId()!=-1){
//                            int id= radioGroup.getCheckedRadioButtonId();
//                            View radioButton = radioGroup.findViewById(id);
//                            int radioId = radioGroup.indexOfChild(radioButton);
//                            RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
//                            String selection = (String) btn.getText();
                            if(!E_Address.getText().toString().equals("")&&!E_City.getText().toString().equals("")
                                    &&!E_Phone.getText().toString().equals("")) {
//                                if(selection.equals("Visa")||selection.equals("بطاقة الخصم / الائتمان")){
//                                  Intent intent=new Intent(getActivity(),PayMent.class);
//                                  intent.putExtra("price",Price);
//                                  startActivity(intent);


//                                }
//                                else if(selection.equals("Cash")||selection.equals("نقدي")){
                                    payment="cash";
//                                  progross_order.setVisibility(View.VISIBLE);
                                    hideSoftKeyboard(getContext(),view);
                                Rela_Login.setAlpha(0.3f);
                                orderBtn.setEnabled(false);
                                progross.setVisibility(View.VISIBLE);

                                order_viewModel.getCreateOrder(payment,E_City.getText().toString()
                                            ,E_Address.getText().toString()  ,Price,E_Phone.getText().toString(),User_token ,getContext()).observe(Check_Order.this, new Observer<Order_Response>() {
                                        @Override
                                        public void onChanged(@Nullable Order_Response tripsData) {
                                            progross.setVisibility(View.GONE);
                                            orderBtn.setEnabled(true);
                                            Rela_Login.setAlpha(1);
                                            if(tripsData!=null){

                                                Toast.makeText(getContext(), getResources().getString(R.string.order), Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(getContext(), TabsLayouts.class));
                                               getActivity().finish();
                                            }else {

                                                Toast.makeText(getContext(), getResources().getString(R.string.failedmsg), Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });



                                }
//                            }


//                        }
                    }
                });



            }
        });


        return view;
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

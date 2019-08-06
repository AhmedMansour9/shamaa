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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Model.Contactus;
import com.shamaa.myapplication.Model.Register_Details;
import com.shamaa.myapplication.Model.UserRegister;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.ContactUs_ViewModel;
import com.shamaa.myapplication.View_Model.Register_ViewModel;

import static com.shamaa.myapplication.Fragments.Login.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_Us extends Fragment {


    public Contact_Us() {
        // Required empty public constructor
    }
    @BindView(R.id.E_Name)
    EditText E_Name;
    @BindView(R.id.E_Phone)
     EditText E_Phone;
    @BindView(R.id.E_Message)
     EditText E_Message;
    @BindView(R.id.SendMesg)
    Button SendMesg;
    @BindView(R.id.progross)
    GifImageView progross;
    @BindView(R.id.Rela_Login)
    RelativeLayout Rela_Login;
    ContactUs_ViewModel contactUs_viewModel;
    String User_token;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_contact__us, container, false);
        ButterKnife.bind(this,view);
        User_token=SharedPrefManager.getInstance(getContext()).getUserToken();
        contactUs_viewModel = ViewModelProviders.of(this).get(ContactUs_ViewModel.class);
        Contact();

        return view;
    }
    public void Contact(){

        SendMesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FUtilsValidation.isEmpty(E_Name,"");
                FUtilsValidation.isEmpty(E_Phone,"");
                FUtilsValidation.isEmpty(E_Message,"");
                if (!E_Name.getText().toString().equals("")
                        && !E_Phone.getText().toString().equals("")
                        && !E_Message.getText().toString().equals("")) {

                    progross.setVisibility(View.VISIBLE);
                    SendMesg.setEnabled(false);
                    Rela_Login.setAlpha(0.3f);
                    hideSoftKeyboard(getContext(),view);
                    contactUs_viewModel.getContactus(E_Name.getText().toString(),E_Phone.getText().toString()
                            ,E_Message.getText().toString(),User_token ,getContext()).observe(Contact_Us.this, new Observer<Contactus>() {
                        @Override
                        public void onChanged(@Nullable Contactus tripsData) {
                            progross.setVisibility(View.GONE);
                            SendMesg.setEnabled(true);
                            Rela_Login.setAlpha(1);
                            if(tripsData!=null){

                                Toast.makeText(getContext(), getResources().getString(R.string.sendsuccess), Toast.LENGTH_SHORT).show();

                            }else {

                                Toast.makeText(getContext(), getResources().getString(R.string.failedmsg), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }}
        });


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
    @Override
    public void onDetach() {
        super.onDetach();
        TabsLayouts.Visablty = true;
    }
}

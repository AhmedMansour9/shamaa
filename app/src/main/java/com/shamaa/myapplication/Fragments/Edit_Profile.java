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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Model.Contactus;
import com.shamaa.myapplication.Model.EditProfile;
import com.shamaa.myapplication.Model.Register_Details;
import com.shamaa.myapplication.Model.UserRegister;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.ContactUs_ViewModel;
import com.shamaa.myapplication.View_Model.EditProfile_ViewModel;
import com.shamaa.myapplication.View_Model.Register_ViewModel;

import static com.shamaa.myapplication.Fragments.Login.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_Profile extends Fragment {


    public Edit_Profile() {
        // Required empty public constructor
    }
    @BindView(R.id.E_Name)
    EditText E_Name;
    @BindView(R.id.E_Phone)
    EditText E_Phone;
    @BindView(R.id.E_Email)
    EditText E_Email;
    @BindView(R.id.Confirm)
    Button Confirm;
    @BindView(R.id.progross)
    GifImageView progross;
    @BindView(R.id.E_Password_Login)
    EditText E_Password_Login;
    @BindView(R.id.Rela_Login)
    RelativeLayout Rela_Login;
    EditProfile_ViewModel contactUs_viewModel;
    String User_token;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit__profile, container, false);
        ButterKnife.bind(this,view);
        User_token= SharedPrefManager.getInstance(getContext()).getUserToken();
        contactUs_viewModel = ViewModelProviders.of(this).get(EditProfile_ViewModel.class);
        Confirm();




        return view;
    }
    public void Confirm(){

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FUtilsValidation.isEmpty(E_Name,"");
                FUtilsValidation.isEmpty(E_Phone,"");
                FUtilsValidation.isEmpty(E_Email,"");
                FUtilsValidation.isEmpty(E_Password_Login,"");
                FUtilsValidation.isLengthCorrect(E_Password_Login.getText().toString(), 8, 16);


                if (ValidateEmail()) {

                    if (E_Password_Login.getText().toString().length() <= 6) {

                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.minpassword), Toast.LENGTH_SHORT).show();

                    }
                    else if (!E_Name.getText().toString().equals("")
                            && !E_Phone.getText().toString().equals("")
                            && !E_Email.getText().toString().equals("")
                            && !E_Password_Login.getText().toString().equals("")) {

                        progross.setVisibility(View.VISIBLE);
                        Confirm.setEnabled(false);
                        Rela_Login.setAlpha(0.3f);
                        hideSoftKeyboard(getContext(),view);
                        contactUs_viewModel.getEditProfile(E_Name.getText().toString(),E_Phone.getText().toString()
                                , E_Email.getText().toString()  ,E_Password_Login.getText().toString(),User_token ,getContext()).observe(Edit_Profile.this, new Observer<EditProfile>() {
                            @Override
                            public void onChanged(@Nullable EditProfile tripsData) {
                                progross.setVisibility(View.GONE);
                                Confirm.setEnabled(true);
                                Rela_Login.setAlpha(1);
                                if(tripsData!=null){

                                    Toast.makeText(getContext(), getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();

                                }else {

                                    Toast.makeText(getContext(), getResources().getString(R.string.failedmsg), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }
                }
            }
        });

    }

    private Boolean ValidateEmail(){
        String EMAIL=E_Email.getText().toString().trim();
        if (EMAIL.isEmpty()||!isValidEmail(EMAIL)){
            FUtilsValidation.isEmpty(E_Email, "");
            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!E_Email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            FUtilsValidation.isEmpty(E_Email,"");
            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

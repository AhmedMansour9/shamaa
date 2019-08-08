package com.shamaa.myapplication.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.shamaa.myapplication.Activities.Reset_Password;
import com.shamaa.myapplication.Activities.Splash;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.Products_Adapter;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.Model.Register_Details;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.Products_ViewModel;
import com.shamaa.myapplication.View_Model.Register_ViewModel;
import com.shamaa.myapplication.Model.UserDetails;
import com.shamaa.myapplication.Model.UserRegister;
import com.shamaa.myapplication.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment  {


    public Login() {
        // Required empty public constructor
    }
   @BindView(R.id.T_SignUp)
    TextView T_SignUp;
    @BindView(R.id.T_Login)
    TextView  T_Login;
    @BindView(R.id.Bc_Relative_Register)
    RelativeLayout Bc_Relative_Register;
    @BindView(R.id.Bc_Relative_Login)
    RelativeLayout Bc_Relative_Login;
    View view;
    @BindView(R.id.E_Password_Login)
    EditText E_Password;
    Register_ViewModel register;
    @BindView(R.id.progross)
    GifImageView progross;
    @BindView(R.id.E_Email_Login)
    EditText E_Email_Login;
    @BindView(R.id.E_Phone)
    EditText E_Phone;
    @BindView(R.id.E_Password_Register)
    EditText E_Password_Register;
    @BindView(R.id.E_Full_Name)
    EditText E_Full_Name;
    @BindView(R.id.Btn_Login)
    Button Btn_Login;
    @BindView(R.id.Rela_Login)
    RelativeLayout Rela_Login;
    @BindView(R.id.Sign_Up)
    Button Sign_Up;
    @BindView(R.id.E_Email_Register)
    EditText E_Email_Register;
    @BindView(R.id.E_ConfirmPassword)
    EditText E_ConfirmPassword;
    @BindView(R.id.forget)
    TextView forget;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        register = ViewModelProviders.of(this).get(Register_ViewModel.class);

        SignUp_OnCilck();
        Login_OnCilck();
        Login();
        Register();
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Reset_Password.class));

            }
        });

      return view;
    }
    public void Login(){

        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FUtilsValidation.isEmpty(E_Email_Login, getActivity().getString(R.string.insertemail));
                FUtilsValidation.isEmpty(E_Password, getActivity().getString(R.string.insertpass));
                FUtilsValidation.isLengthCorrect(E_Password.getText().toString(), 8, 16);
                if (!E_Email_Login.getText().toString().equals("")
                        && !E_Password.getText().toString().equals("")
                        && (FUtilsValidation.isLengthCorrect(E_Password.getText().toString(), 8, 16))) {

                    UserRegister user = new UserRegister();
                    user.setEmail(E_Email_Login.getText().toString());
                    progross.setVisibility(View.VISIBLE);
                    user.setPassword(E_Password.getText().toString());
                    Btn_Login.setEnabled(false);
                    Rela_Login.setAlpha(0.3f);
                    hideSoftKeyboard(getContext(),view);
                    register.getLogin(user,getContext()).observe(Login.this, new Observer<Register_Details>() {
                        @Override
                        public void onChanged(@Nullable Register_Details tripsData) {
                            progross.setVisibility(View.GONE);
                            Rela_Login.setAlpha(1);
                            if(tripsData!=null){
                                Toast.makeText(getContext(), getResources().getString(R.string.loginsuccess), Toast.LENGTH_SHORT).show();
                                SharedPrefManager.getInstance(getContext()).saveUserToken(tripsData.getToken());
                                startActivity(new Intent(getContext(), TabsLayouts.class));
                                getActivity().finish();
                            }else {
                                Register_ViewModel register_viewModel=new Register_ViewModel();
                                String error=register_viewModel.getMsg();
                                if(error!=null){
                                    Toast.makeText(getContext(), ""+getActivity().getResources().getString(R.string.emailfailed), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }}
        });


    }

    public void Register(){

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FUtilsValidation.isEmpty(E_Email_Register, getActivity().getString(R.string.insertemail));
                FUtilsValidation.isEmpty(E_Full_Name, getActivity().getString(R.string.insertfullname));
                FUtilsValidation.isEmpty(E_Phone, getActivity().getString(R.string.insertphone));
                FUtilsValidation.isEmpty(E_Password_Register, getActivity().getString(R.string.insertpass));
                FUtilsValidation.isEmpty(E_ConfirmPassword, getActivity().getString(R.string.insertconfirm));
                FUtilsValidation.isLengthCorrect(E_Password_Register.getText().toString(), 8, 16);
                FUtilsValidation.isLengthCorrect(E_ConfirmPassword.getText().toString(), 8, 16);


                 if (ValidateEmail()) {

                    if (E_Password_Register.getText().toString().length() <= 6) {

                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.minpassword), Toast.LENGTH_SHORT).show();

                    }
                    else  if (!E_Password_Register.getText().toString().equals(E_ConfirmPassword.getText().toString())) {

                         Toast.makeText(getActivity(), "" + getResources().getString(R.string.matchpass), Toast.LENGTH_SHORT).show();

                     }
                    else if (!E_Email_Register.getText().toString().equals("") &&
                                !E_Full_Name.getText().toString().equals("") &&
                                !E_Full_Name.getText().toString().equals("")
                                && !E_Phone.getText().toString().equals("") &&
                                (FUtilsValidation.isLengthCorrect(E_Password_Register.getText().toString(), 8, 16))) {


                            UserRegister user = new UserRegister();
                            user.setEmail(E_Email_Register.getText().toString());
                            user.setFullName(E_Full_Name.getText().toString());
                            user.setPhone(E_Phone.getText().toString());
                            user.setPassword(E_Password_Register.getText().toString());
                            Btn_Login.setEnabled(false);
                            Rela_Login.setAlpha(0.3f);
                            Sign_Up.setEnabled(false);
                            progross.setVisibility(View.VISIBLE);
                         hideSoftKeyboard(getContext(),view);
                        register.getRegister(user,getContext()).observe(Login.this, new Observer<Register_Details>() {
                                @Override
                                public void onChanged(@Nullable Register_Details tripsData) {
                                    progross.setVisibility(View.GONE);
                                    Rela_Login.setAlpha(1);
                                    Toast.makeText(getContext(), getResources().getString(R.string.registersuccess), Toast.LENGTH_SHORT).show();
                                    if(tripsData!=null){
                                        SharedPrefManager.getInstance(getContext()).saveUserToken(tripsData.getToken());
                                        startActivity(new Intent(getContext(), TabsLayouts.class));
                                        getActivity().finish();
                                    }else {
                                        Register_ViewModel register_viewModel=new Register_ViewModel();
                                        String error=register_viewModel.getMsg();
                                        if(error!=null){
                                            Toast.makeText(getContext(), ""+getActivity().getResources().getString(R.string.emailfailed), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            });

                        }


                }
            }
        });


    }

    public void SignUp_OnCilck(){
        T_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T_SignUp.setTextColor(Color.WHITE);
                T_SignUp.setBackgroundColor(view.getResources().getColor(R.color.login_olor));
                T_Login.setTextColor(view.getResources().getColor(R.color.login_olor));
                T_Login.setBackgroundColor(Color.WHITE);
                Bc_Relative_Login.setVisibility(View.GONE);
                Bc_Relative_Register.setVisibility(View.VISIBLE);
            }
        });

    }private Boolean ValidateEmail(){
        String EMAIL=E_Email_Register.getText().toString().trim();
        if (EMAIL.isEmpty()||!isValidEmail(EMAIL)){
            FUtilsValidation.isEmpty(E_Email_Register, "");
            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!E_Email_Register.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            FUtilsValidation.isEmpty(E_Email_Register,"");
            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void Login_OnCilck(){
        T_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T_Login.setTextColor(Color.WHITE);
                T_Login.setBackgroundColor(view.getResources().getColor(R.color.login_olor));
                T_SignUp.setTextColor(view.getResources().getColor(R.color.login_olor));
                T_SignUp.setBackgroundColor(Color.WHITE);
                Bc_Relative_Login.setVisibility(View.VISIBLE);
                Bc_Relative_Register.setVisibility(View.GONE);
            }
        });


    }
    // hide keyboard
    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if(inputMethodManager != null && inputMethodManager.isActive())
        {
            //inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

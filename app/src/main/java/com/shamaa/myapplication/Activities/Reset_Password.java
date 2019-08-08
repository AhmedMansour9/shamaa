package com.shamaa.myapplication.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.shamaa.myapplication.Fragments.Login;
import com.shamaa.myapplication.Model.Register_Details;
import com.shamaa.myapplication.Model.Reset_Response;
import com.shamaa.myapplication.Model.UserRegister;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.Register_ViewModel;
import com.shamaa.myapplication.View_Model.Reset_ViewModel;

public class Reset_Password extends AppCompatActivity {
    @BindView(R.id.E_Email_Reset)
    EditText E_Email_Reset;
    @BindView(R.id.Btn_Reset)
    Button Btn_Reset;
    @BindView(R.id.progross_reset)
    ProgressBar progross_reset;
    Reset_ViewModel reset_viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reset__password);
        ButterKnife.bind(this);
        reset_viewModel = ViewModelProviders.of(this).get(Reset_ViewModel.class);
        ResetEmail();

    }

    public void ResetEmail(){

        Btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FUtilsValidation.isEmpty(E_Email_Reset,"");
                if (!E_Email_Reset.getText().toString().equals("")) {

                    UserRegister user = new UserRegister();
                    user.setEmail(E_Email_Reset.getText().toString());
                    progross_reset.setVisibility(View.VISIBLE);
                    Btn_Reset.setEnabled(false);

                    reset_viewModel.getResent(user,Reset_Password.this).observe(Reset_Password.this, new Observer<Reset_Response>() {
                        @Override
                        public void onChanged(@Nullable Reset_Response tripsData) {
                            progross_reset.setVisibility(View.GONE);

                            if(tripsData!=null){
                                Toast.makeText(Reset_Password.this,tripsData.getMessage() , Toast.LENGTH_SHORT).show();
                               finish();
                            }else {

                            }

                        }
                    });
                }}
        });


    }
}

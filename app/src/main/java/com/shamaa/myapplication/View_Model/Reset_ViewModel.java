package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.RegisterResponse;
import com.shamaa.myapplication.Model.Register_Details;
import com.shamaa.myapplication.Model.Reset_Response;
import com.shamaa.myapplication.Model.UserRegister;
import com.shamaa.myapplication.Retrofit.ApiCLint;
import com.shamaa.myapplication.Retrofit.Apiinterface;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reset_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<Reset_Response> regist;
    String Error;


    public LiveData<Reset_Response> getResent(UserRegister user, Context context) {
        regist = new MutableLiveData<Reset_Response>();
        this.context=context;
        Resent(user);

        return regist;
    }



    public void Resent(UserRegister user) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("lang", "en");
        queryMap.put("email", user.getEmail());
        Apiinterface apiInterface = ApiCLint.getClient().create(Apiinterface.class);
        Call<Reset_Response> call = apiInterface.Resent(queryMap);
        call.enqueue(new Callback<Reset_Response>() {
            @Override
            public void onResponse(Call<Reset_Response> call, Response<Reset_Response> response) {
                if(response.code()==200) {
                    regist.setValue(response.body());
                }else if(response.code()==404){
                    regist.setValue(null);
                }


            }
            @Override
            public void onFailure(Call<Reset_Response> call, Throwable t) {
                regist.setValue(null);
                Error="error";

            }
        });
    }


    public String getMsg(){

        return Error;

    }


}

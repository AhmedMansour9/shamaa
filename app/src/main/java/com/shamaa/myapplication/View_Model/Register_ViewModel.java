package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.Model.RegisterResponse;
import com.shamaa.myapplication.Model.Register_Details;
import com.shamaa.myapplication.Model.UserRegister;
import com.shamaa.myapplication.Retrofit.ApiCLint;
import com.shamaa.myapplication.Retrofit.Apiinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<Register_Details> regist;
    String Error;
    public LiveData<Register_Details> getRegister(UserRegister user, Context context) {
            regist = new MutableLiveData<Register_Details>();
            this.context=context;
            register(user);

        return regist;
    }

    public LiveData<Register_Details> getLogin(UserRegister user, Context context) {
        if (regist == null) {
            regist = new MutableLiveData<Register_Details>();
            this.context=context;
            login(user);
        }
        return regist;
    }

    public void register(UserRegister user) {
        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("name", user.getFullName());
        queryMap.put("password", user.getPassword());
        queryMap.put("email", user.getEmail());
        queryMap.put("phone", user.getPhone());
        queryMap.put("address","a");
        Apiinterface apiInterface = ApiCLint.getClient().create(Apiinterface.class);
        Call<RegisterResponse> call = apiInterface.register(queryMap);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.code()==200) {
                    regist.setValue(response.body().getData());
                }else if(response.code()==404){
                    regist.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
            Error="error";
            regist.setValue(null);
            }
        });
    }

    public void login(UserRegister user) {
        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("email", user.getEmail());
        queryMap.put("password", user.getPassword());
        Apiinterface apiInterface = ApiCLint.getClient().create(Apiinterface.class);
        Call<RegisterResponse> call = apiInterface.login(queryMap);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(response.code()==200) {
                        regist.setValue(response.body().getData());
                    }else if(response.code()==404){
                        regist.setValue(null);
                    }


            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                regist.setValue(null);
                Error="error";

            }
        });
    }


    public String getMsg(){

        return Error;

    }

}

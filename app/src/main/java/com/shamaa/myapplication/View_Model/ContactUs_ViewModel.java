package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.ContactUs_Response;
import com.shamaa.myapplication.Model.Contactus;
import com.shamaa.myapplication.Model.RegisterResponse;
import com.shamaa.myapplication.Model.Register_Details;
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

public class ContactUs_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<Contactus> regist;
    String Error;

    public LiveData<Contactus> getContactus(String Name,String phone,String message,String User_token, Context context) {
        regist = new MutableLiveData<Contactus>();
        this.context=context;
        Contactus(Name,phone,message,User_token);

        return regist;
    }

    public void Contactus(String Name,String phone,String message,String User_token) {
        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("lang", "en");
        queryMap.put("name", Name);
        queryMap.put("phone", phone);
        queryMap.put("message", message);
        Apiinterface apiInterface = ApiCLint.getClient().create(Apiinterface.class);
        Call<ContactUs_Response> call = apiInterface.ContactUs(queryMap,"Bearer "+User_token);
        call.enqueue(new Callback<ContactUs_Response>() {
            @Override
            public void onResponse(Call<ContactUs_Response> call, Response<ContactUs_Response> response) {
                if(response.code()==200) {
                    regist.setValue(response.body().getData());
                }else if(response.code()==404){
                    regist.setValue(null);
                }


            }
            @Override
            public void onFailure(Call<ContactUs_Response> call, Throwable t) {
                regist.setValue(null);

            }
        });
    }

}

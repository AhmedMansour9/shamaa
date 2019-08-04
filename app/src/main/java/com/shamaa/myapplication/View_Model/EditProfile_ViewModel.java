package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.ContactUs_Response;
import com.shamaa.myapplication.Model.Contactus;
import com.shamaa.myapplication.Model.EditProfile;
import com.shamaa.myapplication.Model.EditProfile_Response;
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

public class EditProfile_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<EditProfile> regist;
    String Error;

    public LiveData<EditProfile> getEditProfile(String Name, String phone, String email,String password, String User_token, Context context) {
        regist = new MutableLiveData<EditProfile>();
        this.context=context;
        Contactus(Name,phone,email,password,User_token);

        return regist;
    }

    public void Contactus(String Name,String phone,String email,String password,String User_token) {
        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("lang", "en");
        queryMap.put("name", Name);
        queryMap.put("phone", phone);
        queryMap.put("email", email);
        queryMap.put("password", password);
        Apiinterface apiInterface = ApiCLint.getClient().create(Apiinterface.class);
        Call<EditProfile_Response> call = apiInterface.EditProf(queryMap,"Bearer "+User_token);
        call.enqueue(new Callback<EditProfile_Response>() {
            @Override
            public void onResponse(Call<EditProfile_Response> call, Response<EditProfile_Response> response) {
                if(response.code()==200) {
                    regist.setValue(response.body().getData());
                }else if(response.code()==404){
                    regist.setValue(null);
                }


            }
            @Override
            public void onFailure(Call<EditProfile_Response> call, Throwable t) {
                regist.setValue(null);

            }
        });
    }
}

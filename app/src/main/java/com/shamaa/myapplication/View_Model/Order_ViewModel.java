package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.EditProfile;
import com.shamaa.myapplication.Model.EditProfile_Response;
import com.shamaa.myapplication.Model.Order_Response;
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

public class Order_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<Order_Response> regist;
    String Error;

    public LiveData<Order_Response> getCreateOrder(String payment_type, String city, String address, String totalPrice, String User_token, Context context) {
        regist = new MutableLiveData<Order_Response>();
        this.context=context;
        order(payment_type,city,address,totalPrice,User_token);

        return regist;
    }

    public void order(String payment_type,String city,String address,String totalPrice,String User_token) {
        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("lang", "en");
        queryMap.put("payment_type", payment_type);
        queryMap.put("city", city);
        queryMap.put("address", address);
        queryMap.put("totalPrice", totalPrice);

        Apiinterface apiInterface = ApiCLint.getClient().create(Apiinterface.class);
        Call<Order_Response> call = apiInterface.CreateOrder(queryMap,"Bearer "+User_token);
        call.enqueue(new Callback<Order_Response>() {
            @Override
            public void onResponse(Call<Order_Response> call, Response<Order_Response> response) {
                if(response.code()==200) {
                    regist.setValue(response.body());
                }else if(response.code()==404){
                    regist.setValue(null);
                }


            }
            @Override
            public void onFailure(Call<Order_Response> call, Throwable t) {
                regist.setValue(null);

            }
        });
    }
}

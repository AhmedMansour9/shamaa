package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.CartDetails;
import com.shamaa.myapplication.Model.CartResponse;
import com.shamaa.myapplication.Model.UpdateCart_Response;
import com.shamaa.myapplication.Retrofit.ApiCLint;
import com.shamaa.myapplication.Retrofit.Apiinterface;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCart_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<UpdateCart_Response> tripList;

    public LiveData<UpdateCart_Response> updateCart(String id,String User, String Lang, Context context) {
            tripList = new MutableLiveData<UpdateCart_Response>();
            this.context=context;
            pluscart(id,Lang,User);

        return tripList;
    }

    public LiveData<UpdateCart_Response> updateminusCart(String id,String User, String Lang, Context context) {
            tripList = new MutableLiveData<UpdateCart_Response>();
            this.context=context;
            miuscart(id,Lang,User);
        return tripList;
    }
    public LiveData<UpdateCart_Response> deleteCart(String id,String User, String Lang, Context context) {
            tripList = new MutableLiveData<UpdateCart_Response>();
            this.context=context;
            deletecart(id,Lang,User);

        return tripList;
    }
    public void pluscart( String id,String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("cart_id", id);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<UpdateCart_Response> call = service.UpdateCart(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<UpdateCart_Response>() {
            @Override
            public void onResponse(Call<UpdateCart_Response> call, Response<UpdateCart_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<UpdateCart_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }
    public void miuscart( String id,String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("cart_id", id);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<UpdateCart_Response> call = service.MinusCart(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<UpdateCart_Response>() {
            @Override
            public void onResponse(Call<UpdateCart_Response> call, Response<UpdateCart_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<UpdateCart_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }
    public void deletecart( String id,String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("cart_id", id);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<UpdateCart_Response> call = service.DeleteCart(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<UpdateCart_Response>() {
            @Override
            public void onResponse(Call<UpdateCart_Response> call, Response<UpdateCart_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<UpdateCart_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }
}

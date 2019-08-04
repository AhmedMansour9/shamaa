package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.AddToCart_Response;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Categories_Response;
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

public class AddToCart_ViewModel extends ViewModel {


    Context context;
    private MutableLiveData<AddToCart_Response> tripList;
    String message;
    public String GetMessage(){

        return message;
    }

    public LiveData<AddToCart_Response> AddToCart(String ProductId,String standard_gold_id,String size_id,String usertoken, String Lang, Context context) {
            tripList = new MutableLiveData<AddToCart_Response>();
            this.context=context;
            AddToCart(ProductId,standard_gold_id,size_id,Lang,usertoken);

        return tripList;
    }


    public void AddToCart(String ProductId,String standard_gold_id,String size_id, String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("product_id", ProductId);
        if(standard_gold_id!=null) {
            hashMap.put("standard_gold_id", standard_gold_id);
        }
        if(size_id!=null) {
            hashMap.put("size_id", size_id);
        }
        hashMap.put("qty", "1");
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<AddToCart_Response> call = service.AddToCart(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<AddToCart_Response>() {
            @Override
            public void onResponse(Call<AddToCart_Response> call, Response<AddToCart_Response> response) {

                if (response.code()==200) {
                    message=response.body().getMessage();
                    tripList.setValue(null);

                } else  {
                    tripList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddToCart_Response> call, Throwable t) {
                tripList.setValue(null);

            }
        });
    }

}
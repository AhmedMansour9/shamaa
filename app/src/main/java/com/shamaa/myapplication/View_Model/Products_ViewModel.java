package com.shamaa.myapplication.View_Model;

import android.content.Context;
import android.widget.Toast;

import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.Model.Products_Response;
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

public class Products_ViewModel extends ViewModel {
    Context context;
    private MutableLiveData<List<Products_Model>> tripList;

    public LiveData<List<Products_Model>> getProduct(String Page,String Lang,Context context) {
        if (tripList == null) {
            tripList = new MutableLiveData<List<Products_Model>>();
            this.context=context;
            getProducts(Page,Lang);
        }
        return tripList;
    }

    public void getProducts(String Page, String lang) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("page", Page);

        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Products_Response> call = service.GetProducts(hashMap);
        call.enqueue(new Callback<Products_Response>() {
            @Override
            public void onResponse(Call<Products_Response> call, Response<Products_Response> response) {
                if (response.isSuccessful()) {

                    if (response.body().getCode().equals("200")) {
                        tripList.setValue(response.body().getData());
                    } else if (response.body().getCode().equals("905")) {
                        tripList.setValue(null);
                    }
                }
            }
            @Override
            public void onFailure(Call<Products_Response> call, Throwable t) {
                Toast.makeText(context, ""+call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
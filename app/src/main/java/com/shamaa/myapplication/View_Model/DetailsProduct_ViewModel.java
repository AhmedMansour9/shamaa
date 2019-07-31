package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.DetailsProduct;
import com.shamaa.myapplication.Model.DetailsProduct_Response;
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

public class DetailsProduct_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<List<DetailsProduct>> tripList;

    public LiveData<List<DetailsProduct>> getDetailsProduct(String User, String Page, String Lang, Context context) {
            tripList = new MutableLiveData<List<DetailsProduct>>();
            this.context=context;
            getdetailsProducts(Page,Lang,User);

        return tripList;
    }

    public void getdetailsProducts(String Page, String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("product_id", Page);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<DetailsProduct_Response> call = service.GetDetailsProducts(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<DetailsProduct_Response>() {
            @Override
            public void onResponse(Call<DetailsProduct_Response> call, Response<DetailsProduct_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<DetailsProduct_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }

}

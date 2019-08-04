package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.CartDetails;
import com.shamaa.myapplication.Model.CartResponse;
import com.shamaa.myapplication.Model.DetailsProduct;
import com.shamaa.myapplication.Model.DetailsProduct_Response;
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

public class Cart_ViewModel   extends ViewModel {

    Context context;
    private MutableLiveData<List<CartDetails>> tripList;

    public LiveData<List<CartDetails>> getCart(String User, String Lang, Context context) {
            tripList = new MutableLiveData<List<CartDetails>>();
            this.context = context;
            getcart(Lang, User);

        return tripList;
    }

    public void getcart( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<CartResponse> call = service.GetCart(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }

}

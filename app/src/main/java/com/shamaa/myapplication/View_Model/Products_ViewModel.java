package com.shamaa.myapplication.View_Model;

import android.content.Context;
import android.widget.Toast;

import com.shamaa.myapplication.Model.AddToFavourit;
import com.shamaa.myapplication.Model.AddToFavourit_Response;
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
    private MutableLiveData<AddToFavourit> Add;

    public LiveData<List<Products_Model>> getProduct(String User,String Page,String Lang,Context context) {
            tripList = new MutableLiveData<List<Products_Model>>();
            this.context=context;
            getProducts(Page,Lang,User);

        return tripList;
    }
    public LiveData<List<Products_Model>> getFavourit(String User,String Lang,Context context) {
            tripList = new MutableLiveData<List<Products_Model>>();
            this.context=context;
            getFavourit(Lang,User);

        return tripList;
    }
    public LiveData<AddToFavourit> AddToFavourit(String User, String Page, String Lang, Context context) {
            Add = new MutableLiveData<AddToFavourit>();
            this.context=context;
            getAddToFavourit(Page,Lang,User);

        return Add;
    }

    public void getProducts(String Page, String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("subcategory_id", Page);

        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Products_Response> call = service.GetProducts(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Products_Response>() {
            @Override
            public void onResponse(Call<Products_Response> call, Response<Products_Response> response) {

                    if (response.code()==200) {
                        tripList.setValue(response.body().getData());
                    } else  {
                        tripList.setValue(null);
                    }

            }
            @Override
            public void onFailure(Call<Products_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }

    public void getFavourit(String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);

        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Products_Response> call = service.GetFavourit(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Products_Response>() {
            @Override
            public void onResponse(Call<Products_Response> call, Response<Products_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<Products_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }

    public void getAddToFavourit(String Page, String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("product_id", Page);

        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<AddToFavourit_Response> call = service.AddToFavouritProducts(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<AddToFavourit_Response>() {
            @Override
            public void onResponse(Call<AddToFavourit_Response> call, Response<AddToFavourit_Response> response) {

                if (response.code()==200) {
                    Add.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<AddToFavourit_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }
}
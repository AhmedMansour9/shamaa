package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.Banners;
import com.shamaa.myapplication.Model.Banners_Response;
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

public class Banners_ViewModel  extends ViewModel {

    Context context;
    private MutableLiveData<List<Banners>> tripList;

    public LiveData<List<Banners>> getBanners(String usertoken, String Lang, Context context) {
        if (tripList == null) {
            tripList = new MutableLiveData<List<Banners>>();
            this.context=context;
            getBanner(Lang,usertoken);
        }
        return tripList;
    }

    public void getBanner( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Banners_Response> call = service.GetBanners(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Banners_Response>() {
            @Override
            public void onResponse(Call<Banners_Response> call, Response<Banners_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Banners_Response> call, Throwable t) {
                tripList.setValue(null);

            }
        });
    }

        }

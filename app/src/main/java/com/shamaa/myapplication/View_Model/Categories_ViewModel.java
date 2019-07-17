package com.shamaa.myapplication.View_Model;

import android.content.Context;
import android.widget.Toast;

import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Categories_Response;
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

public class Categories_ViewModel  extends ViewModel {

    Context context;
    private MutableLiveData<List<Categories>> tripList;

    public LiveData<List<Categories>> getCetgroies(String usertoken,String Lang, Context context) {
        if (tripList == null) {
            tripList = new MutableLiveData<List<Categories>>();
            this.context=context;
            getProducts(Lang,usertoken);
        }
        return tripList;
    }
    public LiveData<List<Categories>> getSubCetgroies(String usertoken,String Lang, Context context,String id) {
        if (tripList == null) {
            tripList = new MutableLiveData<List<Categories>>();
            this.context=context;
            getSubCategories(Lang,usertoken,id);
        }
        return tripList;
    }

    public void getProducts( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Categories_Response> call = service.GetCategories(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Categories_Response>() {
            @Override
            public void onResponse(Call<Categories_Response> call, Response<Categories_Response> response) {

                    if (response.code()==200) {
                        tripList.setValue(response.body().getData());
                    } else  {
                        tripList.setValue(null);
                    }
                }

            @Override
            public void onFailure(Call<Categories_Response> call, Throwable t) {
                tripList.setValue(null);

            }
        });
    }
    public void getSubCategories( String lang,String User_token,String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("category_id", id);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Categories_Response> call = service.GetSubCategories(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Categories_Response>() {
            @Override
            public void onResponse(Call<Categories_Response> call, Response<Categories_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Categories_Response> call, Throwable t) {
                tripList.setValue(null);

            }
        });
    }
}

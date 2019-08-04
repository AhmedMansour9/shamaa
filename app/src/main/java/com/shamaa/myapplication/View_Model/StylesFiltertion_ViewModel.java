package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.Banners;
import com.shamaa.myapplication.Model.Banners_Response;
import com.shamaa.myapplication.Model.ClaiberFiltertion;
import com.shamaa.myapplication.Model.ClaiberFiltertion_Response;
import com.shamaa.myapplication.Model.Style_Details;
import com.shamaa.myapplication.Model.Style_Response;
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

public class StylesFiltertion_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<List<Style_Details>> tripList;
    private MutableLiveData<List<ClaiberFiltertion>> claiber;
    public LiveData<List<ClaiberFiltertion>> getStyles(String usertoken, String Lang, Context context) {
        claiber = new MutableLiveData<List<ClaiberFiltertion>>();
            this.context=context;
            getStylest(Lang,usertoken);


        return claiber;
    }
    public LiveData<List<Style_Details>> getSizes(String usertoken, String Lang, Context context) {
            tripList = new MutableLiveData<List<Style_Details>>();
            this.context=context;
            getSizes(Lang,usertoken);


        return tripList;
    }
    public LiveData<List<Style_Details>> getType(String usertoken, String Lang, Context context) {
            tripList = new MutableLiveData<List<Style_Details>>();
            this.context=context;
            getType(Lang,usertoken);


        return tripList;
    }
    public void getType( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Style_Response> call = service.GetType(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Style_Response>() {
            @Override
            public void onResponse(Call<Style_Response> call, Response<Style_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Style_Response> call, Throwable t) {
                tripList.setValue(null);

            }
        });
    }
    public void getStylest( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<ClaiberFiltertion_Response> call = service.GetClaiber(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<ClaiberFiltertion_Response>() {
            @Override
            public void onResponse(Call<ClaiberFiltertion_Response> call, Response<ClaiberFiltertion_Response> response) {

                if (response.code()==200) {
                    claiber.setValue(response.body().getData());
                } else  {
                    claiber.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ClaiberFiltertion_Response> call, Throwable t) {
                claiber.setValue(null);

            }
        });
    }
    public void getSizes( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Style_Response> call = service.GetStyle(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Style_Response>() {
            @Override
            public void onResponse(Call<Style_Response> call, Response<Style_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Style_Response> call, Throwable t) {
                tripList.setValue(null);

            }
        });
    }
}

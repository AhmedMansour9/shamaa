package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Fragments.Profile;
import com.shamaa.myapplication.Model.DetailsProduct;
import com.shamaa.myapplication.Model.DetailsProduct_Response;
import com.shamaa.myapplication.Model.Profile_Details;
import com.shamaa.myapplication.Model.Profile_Response;
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

public class Profile_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<List<Profile_Details>> tripList;

    public LiveData<List<Profile_Details>> getProfile(String User, String Lang, Context context) {
        tripList = new MutableLiveData<List<Profile_Details>>();
        this.context=context;
        getprofile(Lang,User);

        return tripList;
    }

    public void getprofile( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<Profile_Response> call = service.Profile(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<Profile_Response>() {
            @Override
            public void onResponse(Call<Profile_Response> call, Response<Profile_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<Profile_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }

}

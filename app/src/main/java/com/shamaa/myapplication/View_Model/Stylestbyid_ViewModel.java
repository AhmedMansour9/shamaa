package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.SizeByid_Response;
import com.shamaa.myapplication.Model.Sizes;
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

public class Stylestbyid_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<List<Style_Details>> tripList;
    private MutableLiveData<List<Sizes>> MultiSizes;

    public LiveData<List<Style_Details>> getStyles(String usertoken, String Lang, Context context) {
        if (tripList == null) {
            tripList = new MutableLiveData<List<Style_Details>>();
            this.context=context;
            getStylest(Lang,usertoken);

        }
        return tripList;
    }
    public LiveData<List<Sizes>> getSizes(String Productid,String usertoken, String Lang, Context context) {
        if (MultiSizes == null) {
            MultiSizes = new MutableLiveData<List<Sizes>>();
            this.context=context;
            getSizes(Lang,usertoken,Productid);

        }
        return MultiSizes;
    }
    public LiveData<List<Style_Details>> getType(String Productid, String usertoken, String Lang, Context context) {
        if (tripList == null) {
            tripList = new MutableLiveData<List<Style_Details>>();
            this.context=context;
            getType(Lang,usertoken,Productid);

        }
        return tripList;
    }
    public void getType( String lang,String User_token,String Productid) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("product_id",Productid);
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
    public void getStylest( String lang,String User_token) {
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
    public void getSizes( String lang,String User_token,String Productid) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("product_id", Productid);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<SizeByid_Response> call = service.GetSizes(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<SizeByid_Response>() {
            @Override
            public void onResponse(Call<SizeByid_Response> call, Response<SizeByid_Response> response) {

                if (response.code()==200) {
                    if(response.body().getData().size()!=0) {
                        MultiSizes.setValue(response.body().getData());
                    } else  {
                        MultiSizes.setValue(null);
                    }
                } else  {
                    MultiSizes.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SizeByid_Response> call, Throwable t) {
                MultiSizes.setValue(null);

            }
        });
    }
}

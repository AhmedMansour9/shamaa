package com.shamaa.myapplication.View_Model;

import android.content.Context;

import com.shamaa.myapplication.Model.MyOrders;
import com.shamaa.myapplication.Model.MyOrders_Response;
import com.shamaa.myapplication.Model.OrderDetails;
import com.shamaa.myapplication.Model.Order_Response;
import com.shamaa.myapplication.Model.OrdersDetails_Response;
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

public class Orders_ViewModel extends ViewModel {

    Context context;
    private MutableLiveData<List<MyOrders>> tripList;
    private MutableLiveData<List<OrderDetails>> detailslist;

    public LiveData<List<MyOrders>> getOrders(String User, String Lang, Context context) {
        tripList = new MutableLiveData<List<MyOrders>>();
        this.context=context;
        getprofile(Lang,User);

        return tripList;
    }
    public LiveData<List<OrderDetails>> getDetailsOrders(String order_id,String User, String Lang, Context context) {
        detailslist = new MutableLiveData<List<OrderDetails>>();
        this.context=context;
        getdetails(order_id,Lang,User);

        return detailslist;
    }

    public void getprofile( String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<MyOrders_Response> call = service.Orders(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<MyOrders_Response>() {
            @Override
            public void onResponse(Call<MyOrders_Response> call, Response<MyOrders_Response> response) {

                if (response.code()==200) {
                    tripList.setValue(response.body().getData());
                } else  {
                    tripList.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<MyOrders_Response> call, Throwable t) {
                tripList.setValue(null);
            }
        });
    }
    public void getdetails( String order_id,String lang,String User_token) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lang", lang);
        hashMap.put("order_id", order_id);
        Apiinterface service = ApiCLint.getClient().create(Apiinterface.class);
        Call<OrdersDetails_Response> call = service.OrdersDetails(hashMap,"Bearer "+User_token);
        call.enqueue(new Callback<OrdersDetails_Response>() {
            @Override
            public void onResponse(Call<OrdersDetails_Response> call, Response<OrdersDetails_Response> response) {

                if (response.code()==200) {
                    detailslist.setValue(response.body().getData());
                } else  {
                    detailslist.setValue(null);
                }

            }
            @Override
            public void onFailure(Call<OrdersDetails_Response> call, Throwable t) {
                detailslist.setValue(null);
            }
        });
    }
}

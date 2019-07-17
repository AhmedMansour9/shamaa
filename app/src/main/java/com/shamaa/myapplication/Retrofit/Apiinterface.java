package com.shamaa.myapplication.Retrofit;


import com.shamaa.myapplication.Model.Banners_Response;
import com.shamaa.myapplication.Model.Categories_Response;
import com.shamaa.myapplication.Model.Products_Response;
import com.shamaa.myapplication.Model.RegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Ahmed on 13/12/2018.
 */

public interface Apiinterface {


    @POST("login")
    Call<RegisterResponse> login(@QueryMap Map<String, String> queryMab);
    @POST("register")
    Call<RegisterResponse> register(@QueryMap Map<String,String> queryMab);

    @POST("getunitsBylang")
    Call<Products_Response> GetProducts(@QueryMap Map<String, String> queryMab);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/all")
    Call<Categories_Response> GetCategories(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/subcategories")
    Call<Categories_Response> GetSubCategories(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/Banner/home")
    Call<Banners_Response> GetBanners(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

}

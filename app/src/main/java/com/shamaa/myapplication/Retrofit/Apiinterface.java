package com.shamaa.myapplication.Retrofit;


import com.shamaa.myapplication.Model.AddToCart_Response;
import com.shamaa.myapplication.Model.AddToFavourit_Response;
import com.shamaa.myapplication.Model.Banners_Response;
import com.shamaa.myapplication.Model.CartResponse;
import com.shamaa.myapplication.Model.Categories_Response;
import com.shamaa.myapplication.Model.ClaiberFiltertion_Response;
import com.shamaa.myapplication.Model.ContactUs_Response;
import com.shamaa.myapplication.Model.DetailsProduct_Response;
import com.shamaa.myapplication.Model.EditProfile_Response;
import com.shamaa.myapplication.Model.MyOrders_Response;
import com.shamaa.myapplication.Model.Order_Response;
import com.shamaa.myapplication.Model.OrdersDetails_Response;
import com.shamaa.myapplication.Model.Products_Response;
import com.shamaa.myapplication.Model.Profile_Response;
import com.shamaa.myapplication.Model.RegisterResponse;
import com.shamaa.myapplication.Model.SizeByid_Response;
import com.shamaa.myapplication.Model.Style_Response;
import com.shamaa.myapplication.Model.UpdateCart_Response;

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

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/products")
    Call<Products_Response> GetProducts(@QueryMap Map<String, String> queryMab,@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/filter/product")
    Call<Products_Response> GetFilterProducts(@QueryMap Map<String, String> queryMab,@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/fave/product")
    Call<Products_Response> GetFavourit(@QueryMap Map<String, String> queryMab,@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/best/diamond")
    Call<Products_Response> GetDiamond(@QueryMap Map<String, String> queryMab,@Header("Authorization") String auth);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/add/fave/product")
    Call<AddToFavourit_Response> AddToFavouritProducts(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/contact/us")
    Call<ContactUs_Response> ContactUs(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/edit/profile")
    Call<EditProfile_Response> EditProf(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/create/order")
    Call<Order_Response> CreateOrder(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/products/detalis")
    Call<DetailsProduct_Response> GetDetailsProducts(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/profile")
    Call<Profile_Response> Profile(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/myorder")
    Call<MyOrders_Response> Orders(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/order/details")
    Call<OrdersDetails_Response> OrdersDetails(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/cart")
    Call<CartResponse> GetCart(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/plusCart")
    Call<UpdateCart_Response> UpdateCart(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/minCart")
    Call<UpdateCart_Response> MinusCart(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/deleteCart")
    Call<UpdateCart_Response> DeleteCart(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/all")
    Call<Categories_Response> GetCategories(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/add/cart")
    Call<AddToCart_Response> AddToCart(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/subcategories")
    Call<Categories_Response> GetSubCategories(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/Banner/home")
    Call<Banners_Response> GetBanners(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/standard/gold/product")
    Call<Style_Response> GetStyle(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/style")
    Call<ClaiberFiltertion_Response> GetClaiber(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/standard/gold")
    Call<Style_Response> GetType(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("categories/size/product")
    Call<SizeByid_Response> GetSizes(@QueryMap Map<String, String> queryMab, @Header("Authorization") String auth);

}

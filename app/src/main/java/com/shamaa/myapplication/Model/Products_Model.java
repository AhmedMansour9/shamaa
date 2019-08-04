package com.shamaa.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Products_Model implements Serializable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("SalesPrice")
    @Expose
    private String salesPrice;
    @SerializedName("OriginalPrice")
    @Expose
    private String originalPrice;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("favorite")
    @Expose
    private String favorite;
    @SerializedName("caliber")
    @Expose
    private String caliber;
    @SerializedName("Rate")
    @Expose
    private String rate;
    @SerializedName("total_rate_av")
    @Expose
    private String totalRateAv;


    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalRateAv() {
        return totalRateAv;
    }

    public void setTotalRateAv(String totalRateAv) {
        this.totalRateAv = totalRateAv;
    }
}
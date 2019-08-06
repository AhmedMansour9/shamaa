package com.shamaa.myapplication.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.View.DetailsProduct_id;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class Favourit_Adapter extends RecyclerView.Adapter<Favourit_Adapter.MyViewHolder> {

    private List<Products_Model> filteredList = new ArrayList<>();
    View itemView;
    Context con;
    DetailsProduct_id detailsProduct_id;
    double value, value2;
    String Price;
    //    ListUnitDetails_View listUnitDetails_view;
    List<Products_Model> list = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView T_title, Price, T_Caliber, T_Price, T_OfferPrice, T_Offer;
        private Button Callnow, Details;
        private ImageView Image_Unit, Img_Favourit, img_product;
        private ProgressBar ProgrossSpare;
        private ImageView person_image, Starone, Startwo, StarThree, StarFour, StarFive;
        RelativeLayout Rela_Product;

        public MyViewHolder(View view) {
            super(view);
            T_Caliber = view.findViewById(R.id.T_Caliber);
            T_Price = view.findViewById(R.id.T_Price);
            T_OfferPrice = view.findViewById(R.id.T_OfferPrice);
            img_product = view.findViewById(R.id.img_product);
            T_title = view.findViewById(R.id.T_title);
            ProgrossSpare = view.findViewById(R.id.progross);
            Rela_Product = view.findViewById(R.id.Rela_Product);
            Img_Favourit = view.findViewById(R.id.Img_Favourit);
            T_Offer = view.findViewById(R.id.T_Offer);

        }
    }

    public Favourit_Adapter(List<Products_Model> list, Context context) {
        this.filteredList = list;
        this.con = context;
    }

    public Favourit_Adapter(List<Products_Model> list) {
        this.filteredList = list;

    }

    public void setOnClicklistner(DetailsProduct_id product_id) {
        this.detailsProduct_id = product_id;
    }


    @Override
    public Favourit_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        return new Favourit_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final Favourit_Adapter.MyViewHolder holder, final int position) {
        holder.T_title.setText(filteredList.get(position).getName());
        holder.T_Caliber.setText(filteredList.get(position).getCaliber());
        value = Double.parseDouble(filteredList.get(position).getOriginalPrice());
        value = Double.parseDouble(new DecimalFormat("##.####").format(value));
        holder.T_OfferPrice.setText(String.valueOf(value));
        holder.T_OfferPrice.setPaintFlags(holder.T_OfferPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        value2 = Double.parseDouble(filteredList.get(position).getSalesPrice());
        value2 = Double.parseDouble(new DecimalFormat("##.####").format(value2));
        holder.T_Price.setText(String.valueOf(value2)+" "+con.getResources().getString(R.string.currency));



        if (!filteredList.get(position).getOffer().equals("0")) {
            holder.T_Offer.setVisibility(View.VISIBLE);
            holder.T_Offer.setText(filteredList.get(position).getOffer()+" "+con.getResources().getString(R.string.offer));
        } else {
            holder.T_Offer.setVisibility(View.GONE);
        }
        if (position % 2 == 0) {
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            linearParams.setMargins(0, 0, 0, 70);
            holder.Rela_Product.setLayoutParams(linearParams);
            holder.Rela_Product.requestLayout();

        } else {
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            linearParams.setMargins(0, 70, 0, 0);
            holder.Rela_Product.setLayoutParams(linearParams);
            holder.Rela_Product.requestLayout();

        }
        if (filteredList.get(position).getFavorite().equals("1")) {
            holder.Img_Favourit.setBackgroundResource(R.drawable.ic_favouritheart);
        } else {
            holder.Img_Favourit.setBackgroundResource(R.drawable.ic_favourit);
        }

        holder.Img_Favourit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filteredList.get(position).getFavorite().equals("1")) {
                    filteredList.get(position).setFavorite("0");
                    holder.Img_Favourit.setBackgroundResource(R.drawable.ic_favourit);
                    detailsProduct_id.AddToFavourit(String.valueOf(filteredList.get(position).getId()));
                    filteredList.remove(position);
                    notifyDataSetChanged();

                } else {
                    filteredList.get(position).setFavorite("1");
                    holder.Img_Favourit.setBackgroundResource(R.drawable.ic_favouritheart);
                    detailsProduct_id.AddToFavourit(String.valueOf(filteredList.get(position).getId()));

                }
            }
        });
        String i = filteredList.get(position).getPhoto();
        Uri u = Uri.parse(i);

        Glide.with(con)
                .load("http://emarketingbakers.com/shama/public/uploads/topics/" + u)
                .apply(new RequestOptions().override(500, 500))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            holder.ProgrossSpare.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            holder.ProgrossSpare.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.img_product);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsProduct_id.Details(filteredList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

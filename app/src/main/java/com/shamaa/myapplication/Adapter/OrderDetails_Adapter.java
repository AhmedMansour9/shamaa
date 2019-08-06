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
import com.shamaa.myapplication.Model.OrderDetails;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.View.DetailsProduct_id;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetails_Adapter extends RecyclerView.Adapter<OrderDetails_Adapter.MyViewHolder> {

    private List<OrderDetails> filteredList = new ArrayList<>();
    View itemView;
    Context con;
    DetailsProduct_id detailsProduct_id;
    double value, value2;
    String Price;
    //    ListUnitDetails_View listUnitDetails_view;
    List<OrderDetails> list = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView T_title, Price, T_Caliber, T_Price, T_OfferPrice, T_Offer;
        private Button Callnow, Details;
        private ImageView Image_Unit, Img_Favourit, img_product;
        private ProgressBar ProgrossSpare;
        private ImageView person_image, Starone, Startwo, StarThree, StarFour, StarFive;
        RelativeLayout Rela_Product;

        public MyViewHolder(View view) {
            super(view);
            T_Price = view.findViewById(R.id.T_Price);
            img_product = view.findViewById(R.id.img_product);
            T_title = view.findViewById(R.id.T_title);

        }
    }

    public OrderDetails_Adapter(List<OrderDetails> list, Context context) {
        this.filteredList = list;
        this.con = context;
    }

    public OrderDetails_Adapter(List<OrderDetails> list) {
        this.filteredList = list;

    }

    public void setOnClicklistner(DetailsProduct_id product_id) {
        this.detailsProduct_id = product_id;
    }


    @Override
    public OrderDetails_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_orderdetails, parent, false);
        return new OrderDetails_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final OrderDetails_Adapter.MyViewHolder holder, final int position) {
        holder.T_title.setText(filteredList.get(position).getName());

        value2 = Double.parseDouble(filteredList.get(position).getPrice());
        value2 = Double.parseDouble(new DecimalFormat("##.####").format(value2));
        holder.T_Price.setText(String.valueOf(value2)+" "+con.getResources().getString(R.string.currency));



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

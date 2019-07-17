package com.shamaa.myapplication.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamaa.myapplication.Model.Banners;
import com.shamaa.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Banner_Adapter extends RecyclerView.Adapter<Banner_Adapter.MyViewHolder>{

    private List<Banners> filteredList=new ArrayList<>();
    SharedPreferences.Editor share;

    public static String TotalPrice;
    View itemView;
    Context con;
    String prrice;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView T_Name,T_Discrption,T_Model,T_Price,count;
        ImageView mobile;
        ProgressBar progressBar;
        ImageView btncart;
        public ImageView plus,minus,delete;
        ImageView imggg;
        public MyViewHolder(View view) {
            super(view);
//            T_Name = view.findViewById(R.id.T_Name);
            imggg=view.findViewById(R.id.viewPagerItem_image1);

        }


    }

    public Banner_Adapter(List<Banners> list, Context context){
        this.filteredList=list;
        this.con=context;
    }


    @Override
    public Banner_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner, parent, false);
        return new Banner_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Banner_Adapter.MyViewHolder holder, final int position) {


        String i = filteredList.get(position).getImage();
        Uri u = Uri.parse(i);
//        holder.progressBar.setVisibility(View.VISIBLE);


        Glide.with(con)
                .load("http://emarketingbakers.com/shama/public/uploads/banners/"+u)
                .apply(new RequestOptions())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            holder.ProgrossSpare.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                })
                .into(holder.imggg);



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

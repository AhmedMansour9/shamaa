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
import com.shamaa.myapplication.Model.ClaiberFiltertion;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.View.DetailsProduct_id;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class ClaiberFiltertion_Adapter extends RecyclerView.Adapter<ClaiberFiltertion_Adapter.MyViewHolder>{

    private List<ClaiberFiltertion> filteredList=new ArrayList<>();
    View itemView;
    Context con;
    DetailsProduct_id detailsProduct_id;
    double value,value2;
    String Styleid;
    String Price;
    //    ListUnitDetails_View listUnitDetails_view;
    List<Products_Model> list=new ArrayList<>();
    int row_index=0;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView T_title,Price,T_Caliber,T_Price,T_OfferPrice,T_Offer;
        private Button Callnow,Details;
        private ImageView Image_Unit,Img_Favourit,img_product;
        private ProgressBar ProgrossSpare;
        private ImageView person_image,Starone,Startwo,StarThree,StarFour,StarFive;
        RelativeLayout Rela_Product,relative_row;

        public MyViewHolder(View view) {
            super(view);
            T_Caliber=view.findViewById(R.id.T_Caliber);
            T_Price=view.findViewById(R.id.T_Price);
            T_OfferPrice=view.findViewById(R.id.T_OfferPrice);
            img_product=view.findViewById(R.id.img_product);
            T_title=view.findViewById(R.id.T_title);
            ProgrossSpare=view.findViewById(R.id.progross);
            Rela_Product=view.findViewById(R.id.Rela_Product);
            Img_Favourit=view.findViewById(R.id.Img_Favourit);
            T_Offer=view.findViewById(R.id.T_Offer);

        }
    }

    public ClaiberFiltertion_Adapter(List<ClaiberFiltertion> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public ClaiberFiltertion_Adapter(List<ClaiberFiltertion> list){
        this.filteredList=list;

    }


    @Override
    public ClaiberFiltertion_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_style, parent, false);
        return new ClaiberFiltertion_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final ClaiberFiltertion_Adapter.MyViewHolder holder, final int position) {
        holder.T_title.setText(filteredList.get(position).getStyleName());
        String i = filteredList.get(position).getStyleImage();
        Uri u = Uri.parse(i);
        if(row_index==position){
            holder.Rela_Product.setBackground(con.getResources().getDrawable(R.drawable.bc_package));
            Styleid=filteredList.get(position).getStyleId();
        }
        else
        {
            holder.Rela_Product.setBackgroundColor(con.getResources().getColor(R.color.white));
        }

       holder.Rela_Product.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               row_index=position;
               notifyDataSetChanged();

           }
       });
        Glide.with(con)
                .load("http://emarketingbakers.com/shama/public/uploads/topics/"+ u)
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

    public String getStyleId(){


        return Styleid;
    };

}


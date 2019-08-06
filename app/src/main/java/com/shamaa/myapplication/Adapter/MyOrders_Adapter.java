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
import com.shamaa.myapplication.Model.MyOrders;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.View.DetailsProduct_id;
import com.shamaa.myapplication.View.OrderId;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrders_Adapter extends RecyclerView.Adapter<MyOrders_Adapter.MyViewHolder>{

    private List<MyOrders> filteredList=new ArrayList<>();
    View itemView;
    Context con;
    OrderId orderId;
    double value,value2;
    String Price;
    //    ListUnitDetails_View listUnitDetails_view;
    List<MyOrders> list=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView T_Date,Price,T_Orderid,T_Price,T_Statues,T_Offer;
        private Button Callnow,Details;
        private ImageView Image_Unit,Img_Favourit,img_product;
        private ProgressBar ProgrossSpare;
        private ImageView person_image,Starone,Startwo,StarThree,StarFour,StarFive;
        RelativeLayout Rela_Product;

        public MyViewHolder(View view) {
            super(view);
            T_Date=view.findViewById(R.id.T_Date);
            T_Price=view.findViewById(R.id.T_Price);
            T_Orderid=view.findViewById(R.id.T_Orderid);
            T_Statues=view.findViewById(R.id.T_Statues);
        }
    }

    public MyOrders_Adapter(List<MyOrders> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public MyOrders_Adapter(List<MyOrders> list){
        this.filteredList=list;

    }
    public void setOnClicklistner(OrderId product_id){
        this.orderId=product_id;
    }


    @Override
    public MyOrders_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_myorders, parent, false);
        return new MyOrders_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final MyOrders_Adapter.MyViewHolder holder, final int position) {
        holder.T_Price.setText(filteredList.get(position).getTotalPrice());
        holder.T_Date.setText(filteredList.get(position).getOrderDate());
        holder.T_Orderid.setText(con.getResources().getString(R.string.orderid)+" "+filteredList.get(position).getId());
        holder.T_Statues.setText(filteredList.get(position).getStatus());


//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderId.id(String.valueOf(filteredList.get(position).getId()));
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


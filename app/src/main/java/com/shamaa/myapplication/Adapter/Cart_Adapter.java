package com.shamaa.myapplication.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.shamaa.myapplication.Fragments.Cart;
import com.shamaa.myapplication.Model.CartDetails;
import com.shamaa.myapplication.Model.CartResponse;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.View.Count_View;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Ahmed on 14/10/2018.
 */

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder>{

    private List<CartDetails> filteredList=new ArrayList<>();
    View itemView;
    Context con;
    Count_View count_view;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView VendorName,T_Price,placeName,T_qount,Phone,telephone,textRate;
        private Button Callnow,Details;
        private ImageView person_image,img_delete,plus,minus;
        public MyViewHolder(View view) {
            super(view);
            person_image=view.findViewById(R.id.img_product);
            VendorName=view.findViewById(R.id.T_title);
            T_Price=view.findViewById(R.id.T_Price);
            plus=view.findViewById(R.id.T_Plus);
            T_qount=view.findViewById(R.id.T_Counter);
            minus=view.findViewById(R.id.T_Minus);
            img_delete=view.findViewById(R.id.img_delete);
        }
    }

    public Cart_Adapter(List<CartDetails> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public void count(Count_View count_view){
        this.count_view=count_view;
    }
    @Override
    public Cart_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        return new Cart_Adapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final Cart_Adapter.MyViewHolder holder, final int position) {
        holder.VendorName.setText(filteredList.get(position).getName());
        holder.T_Price.setText(con.getResources().getString(R.string.totalprice)+" : "+filteredList.get(position).getTotalPrice()+con.getResources().getString(R.string.currency));
        holder.T_qount.setText(filteredList.get(position).getQty());

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=Integer.parseInt(holder.T_qount.getText().toString());
                count++;
                holder.T_qount.setText(count + "");

                count_view.count_plus(String.valueOf(filteredList.get(position).getId()));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=Integer.parseInt(holder.T_qount.getText().toString());
                if(count>1) {
                    count--;
                    holder.T_qount.setText(count + "");
                    count_view.count_minus(String.valueOf(filteredList.get(position).getId()));
                    if (String.valueOf(filteredList.get(position).getQty()).equals("1")) {
                        filteredList.remove(position);
                    }
                }

            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=Integer.parseInt(holder.T_qount.getText().toString());
                if(count>1) {
                    count--;
                    holder.T_qount.setText(count + "");
                    count_view.count_minus(String.valueOf(filteredList.get(position).getId()));
                    if (String.valueOf(filteredList.get(position).getQty()).equals("1")) {
                        filteredList.remove(position);
                    }
                }

            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    count_view.count_delete(String.valueOf(filteredList.get(position).getId()));
//                    if (String.valueOf(filteredList.get(position).getQty()).equals("1")) {
//                        filteredList.remove(position);
//                    }
                notifyDataSetChanged();
                }


        });
        String i = filteredList.get(position).getName();
        Uri u = Uri.parse(i);


    }

    @Override
    public int getItemCount() {
//        return filteredList.size();
        return filteredList.size()  ;
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

package com.shamaa.myapplication.Adapter;

import android.content.Context;
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
import com.shamaa.myapplication.Model.Product_Details;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.MyViewHolder>{

    private List<Products_Model> filteredList=new ArrayList<>();
    View itemView;
    Context con;
//    Details_Sparts details_sparts;
//    phone_view phoneView;
    String Price;
//    ListUnitDetails_View listUnitDetails_view;
    List<Products_Model> list=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Address,Price,Distance_Area,T_Date,Title,T_Type,Text_RegularPrice;
        private Button Callnow,Details;
        private ImageView Image_Unit,call;
        private ProgressBar ProgrossSpare;
        private ImageView person_image,Starone,Startwo,StarThree,StarFour,StarFive;
        RelativeLayout Rela_Product;

        public MyViewHolder(View view) {
            super(view);
//            Address=view.findViewById(R.id.Address);
//            Price=view.findViewById(R.id.Price);
//            Distance_Area=view.findViewById(R.id.Distance_Area);
//            T_Date=view.findViewById(R.id.T_Date);
            Title=view.findViewById(R.id.Title);
            ProgrossSpare=view.findViewById(R.id.progross);
            Rela_Product=view.findViewById(R.id.Rela_Product);

        }
    }

    public Products_Adapter(List<Products_Model> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public Products_Adapter(List<Products_Model> list){
        this.filteredList=list;

    }
//    public void setClickListener(ListUnitDetails_View listUnitDetails_view) {
//        this.listUnitDetails_view = listUnitDetails_view;
//
//    }

    @Override
    public Products_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        return new Products_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final Products_Adapter.MyViewHolder holder, final int position) {


        if(position%2 == 0){
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            linearParams.setMargins(0, 0, 0, 70);
            holder.Rela_Product.setLayoutParams(linearParams);
            holder.Rela_Product.requestLayout();

        }else{
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            linearParams.setMargins(0, 70, 0, 0);
            holder.Rela_Product.setLayoutParams(linearParams);
            holder.Rela_Product.requestLayout();

        }





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


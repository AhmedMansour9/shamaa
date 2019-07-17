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
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategories_Adapter extends RecyclerView.Adapter<SubCategories_Adapter.MyViewHolder>{

    private List<Categories> filteredList=new ArrayList<>();
    View itemView;
    Context con;
    //    Details_Sparts details_sparts;
//    phone_view phoneView;
    String Price;
    //    ListUnitDetails_View listUnitDetails_view;
    List<Categories> list=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Address,Price,Distance_Area,T_Date,Title,T_Type,Text_RegularPrice;
        private Button Callnow,Details;
        private ImageView Image_Unit,call;
        private ProgressBar ProgrossSpare;
        private ImageView img_subcategories,Starone,Startwo,StarThree,StarFour,StarFive;
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
            img_subcategories=view.findViewById(R.id.img_subcategories);

        }
    }

    public SubCategories_Adapter(List<Categories> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public SubCategories_Adapter(List<Categories> list){
        this.filteredList=list;

    }
//    public void setClickListener(ListUnitDetails_View listUnitDetails_view) {
//        this.listUnitDetails_view = listUnitDetails_view;
//
//    }

    @Override
    public SubCategories_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_subcategory, parent, false);
        return new SubCategories_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final SubCategories_Adapter.MyViewHolder holder, final int position) {


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

        holder.Title.setText(filteredList.get(position).getName());
        String i = filteredList.get(position).getPhoto();
        Uri u = Uri.parse(i);
//        holder.ProgrossSpare.setVisibility(View.VISIBLE);
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
                .into(holder.img_subcategories);




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


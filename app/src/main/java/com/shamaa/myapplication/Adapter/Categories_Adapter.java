package com.shamaa.myapplication.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.View.SubCategoryid_View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Categories_Adapter extends RecyclerView.Adapter<Categories_Adapter.MyViewHolder>{

    private List<Categories> filteredList=new ArrayList<>();
    View itemView;
    Context con;
    SubCategoryid_View subCategoryid_view;
    private int postionn=0;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Text_Name;
        private Button Callnow,Details;
        private ImageView img_Category;
        private ProgressBar ProgrossSpare;
        public MyViewHolder(View view) {
            super(view);
            img_Category=view.findViewById(R.id.image_Category);
            Text_Name=view.findViewById(R.id.T_Name);
            ProgrossSpare=view.findViewById(R.id.Progrossbarcategory);
        }

    }

    public Categories_Adapter(List<Categories> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public Categories_Adapter(Context context){

        this.con=context;
    }
    public void setOnClicklistner(SubCategoryid_View product_id){
        this.subCategoryid_view=product_id;
    }

    @Override
    public Categories_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);
        return new Categories_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Categories_Adapter.MyViewHolder holder, final int position) {
        if(position==postionn) {
            holder.itemView.performClick();
            holder.itemView.setSelected(true);
            holder.itemView.setClickable(true);
        }

        holder.Text_Name.setText(filteredList.get(position).getName());
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
                .into(holder.img_Category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoryid_view.id(filteredList.get(position).getId());
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


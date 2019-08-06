package com.shamaa.myapplication.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.shamaa.myapplication.Activities.MainActivity;
import com.shamaa.myapplication.Activities.TabsLayouts;
import com.shamaa.myapplication.Adapter.Banner_Adapter;
import com.shamaa.myapplication.Adapter.BannersProduct_Adapter;
import com.shamaa.myapplication.Adapter.Cart_Adapter;
import com.shamaa.myapplication.Adapter.SubCategories_Adapter;
import com.shamaa.myapplication.CustomSearchableSpinner;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.Model.AddToCart_Response;
import com.shamaa.myapplication.Model.CartDetails;
import com.shamaa.myapplication.Model.Categories;
import com.shamaa.myapplication.Model.DetailsProduct;
import com.shamaa.myapplication.Model.Products_Model;
import com.shamaa.myapplication.Model.Sizes;
import com.shamaa.myapplication.Model.Slider_Banners;
import com.shamaa.myapplication.Model.Style_Details;
import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;
import com.shamaa.myapplication.View_Model.AddToCart_ViewModel;
import com.shamaa.myapplication.View_Model.Cart_ViewModel;
import com.shamaa.myapplication.View_Model.Categories_ViewModel;
import com.shamaa.myapplication.View_Model.DetailsProduct_ViewModel;
import com.shamaa.myapplication.View_Model.Stylestbyid_ViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details_Product extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    public Details_Product() {
        // Required empty public constructor
    }
    String Lang;
    Cart_ViewModel tripsViewModel;
    @BindView(R.id.T_Price)
    TextView T_Price;
    @BindView(R.id.T_Claiber)
    TextView T_Claiber;
    @BindView(R.id.T_Title)
    TextView T_Title;
    @BindView(R.id.T_Details)
    TextView T_Details;
    Products_Model products_model;
    @BindView(R.id.Style_Spinner)
    Spinner Style_Spinner;
    @BindView(R.id.Size_Spinner)
    Spinner Size_Spinner;
    @BindView(R.id.recycler_banner2)
    RecyclerView recycler_banner2;
    @BindView(R.id.Scroll_Details)
    ScrollView Scroll_Details;
    @BindView(R.id.progross)
    GifImageView progross;
    List<Slider_Banners> listBanners;
    String Product_id;
    @BindView(R.id.Btn_AddCart)
    Button Btn_AddCart;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.T_TotalRate)
    TextView T_TotalRate;
    Stylestbyid_ViewModel stylestbyid_viewModel;
    DetailsProduct_ViewModel detailsProduct_viewModel;
    BannersProduct_Adapter bannersProduct_adapter;
    View view;
    @BindView(R.id.swipe_Banners)
    SwipeRefreshLayout swipe_Products;
    final Handler handler = new Handler();
    int position = 0;
    String User_Token,Size,Size_id,Style,Style_id;
    ArrayAdapter<Sizes> listSizes;
    ArrayAdapter<Style_Details> listStylest;
    Boolean end;
    AddToCart_ViewModel addToCart_viewModel;
    final Runnable update = new Runnable() {
        public void run() {
            if(position == listBanners.size()-1){
                end = true;
            }
            else if (position == 0) {
                end = false;
            }
            if(!end){
                position++;
            } else {
                position--;
            }
//                vp_slider.setCurrentItem(page_position, true);
            recycler_banner2.smoothScrollToPosition(position);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_details__product, container, false);
        detailsProduct_viewModel = ViewModelProviders.of(this).get(DetailsProduct_ViewModel.class);
        ButterKnife.bind(this,view);
        tripsViewModel = ViewModelProviders.of(this).get(Cart_ViewModel.class);
        Language();
        getData();
        SwipRefresh();
        AddtoCart();
        return view;
    }

    public void getData(){
        User_Token= SharedPrefManager.getInstance(getContext()).getUserToken();
        Bundle bundle=getArguments();
        if(bundle!=null){
            products_model= (Products_Model) bundle.getSerializable("details");
            T_Price.setText(products_model.getSalesPrice());
            T_Title.setText(products_model.getName());
            T_Claiber.setText(products_model.getCaliber());
            T_Details.setText(products_model.getDetails());
            Product_id=String.valueOf(products_model.getId());
            if(products_model.getTotalRateAv()!=null){
                ratingBar.setRating(Integer.parseInt(products_model.getTotalRateAv()));
            }
            T_TotalRate.setText(products_model.getRate()+" "+getActivity().getResources().getString(R.string.review));
        }
    }
    public void SwipRefresh(){
        swipe_Products.setOnRefreshListener(this);

        swipe_Products.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipe_Products.post(new Runnable() {
            @Override
            public void run() {
                GetBanners();
                GetSizes();
                GetStylest();

            }
        });
    }
    public void GetSizes(){
        Scroll_Details.setAlpha(0.3f);
        progross.setVisibility(View.VISIBLE);
        stylestbyid_viewModel = ViewModelProviders.of(this).get(Stylestbyid_ViewModel.class);
        stylestbyid_viewModel.getSizes(Product_id,User_Token,Lang,getContext()).observe(this, new Observer<List<Sizes>>() {
            @Override
            public void onChanged(@Nullable List<Sizes> tripsData) {
                Scroll_Details.setAlpha(1);
                progross.setVisibility(View.GONE);
               if(tripsData!=null) {
                   listSizes = new ArrayAdapter<Sizes>(getActivity(), R.layout.textcolorspinner, tripsData) {
                       @Override
                       public View getDropDownView(int position, View convertView, ViewGroup parent) {
                           TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                           textView.setTextColor(Color.WHITE);
                           return textView;
                       }
                   };
                   listSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   Size_Spinner.setAdapter(listSizes);
                   Size_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                           Size = Size_Spinner.getSelectedItem().toString();

                           for (i = 0; i < tripsData.size(); i++) {
                               if (tripsData.get(i).getSize().equals(Size)) {
                                   Size_id = String.valueOf(tripsData.get(i).getId());
                               }
                           }
                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> adapterView) {

                       }
                   });
               }else {
                   Size_Spinner.setVisibility(View.GONE);
               }
            }
        });

    }

    public void GetStylest(){
        stylestbyid_viewModel = ViewModelProviders.of(this).get(Stylestbyid_ViewModel.class);
        stylestbyid_viewModel.getType(Product_id,User_Token,Lang,getContext()).observe(this, new Observer<List<Style_Details>>() {
            @Override
            public void onChanged(@Nullable List<Style_Details> tripsData) {
                if(tripsData!=null) {
                    listStylest = new ArrayAdapter<Style_Details>(getActivity(), R.layout.textcolorspinner, tripsData) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                            textView.setTextColor(Color.WHITE);
                            return textView;
                        }
                    };
                    listStylest.setDropDownViewResource(R.layout.spinner_dropdown_item);

                    Style_Spinner.setPrompt(getResources().getString(R.string.selectcaliber));

                    Style_Spinner.setAdapter(listStylest);
                    Style_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Style = Style_Spinner.getSelectedItem().toString();


                            for (i = 0; i < tripsData.size(); i++) {
                                if (tripsData.get(i).getName().equals(Style)) {
                                    Style_id = String.valueOf(tripsData.get(i).getId());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }else {
                    Style_Spinner.setVisibility(View.GONE);
                }


            }
        });

    }
    public void GetBanners(){

        detailsProduct_viewModel.getDetailsProduct(User_Token,Product_id,Lang,getContext()).observe(this, new Observer<List<DetailsProduct>>() {
            @Override
            public void onChanged(@Nullable List<DetailsProduct> tripsData) {
                if (tripsData != null) {
                    listBanners = tripsData.get(0).getImages();
                    bannersProduct_adapter = new BannersProduct_Adapter(listBanners, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recycler_banner2.setLayoutManager(linearLayoutManager);
                    recycler_banner2.setAdapter(bannersProduct_adapter);
                    if(listBanners.size()>1) {
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        }, 2000, 2000);
                    }
                }

            }
        });

    }

    public void AddtoCart(){

        Btn_AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scroll_Details.setAlpha(0.3f);
                progross.setVisibility(View.VISIBLE);

                addToCart_viewModel = ViewModelProviders.of(Details_Product.this).get(AddToCart_ViewModel.class);
                addToCart_viewModel.AddToCart(Product_id,Style_id,Size_id,User_Token,"en",getContext()).observe(Details_Product.this, new Observer <AddToCart_Response>() {
                    @Override
                    public void onChanged(@Nullable AddToCart_Response tripsData) {
                        Scroll_Details.setAlpha(1);
                        progross.setVisibility(View.GONE);
                      String Message=addToCart_viewModel.GetMessage();
                      if(Message!=null) {
                          if (Message.equals("User add Cart successfully.")) {
                              Toast.makeText(getContext(), "" + getResources().getString(R.string.addedtocartsuccess), Toast.LENGTH_SHORT).show();

                              tripsViewModel.getCart(User_Token,Lang,getContext()).observe(getActivity(), new Observer<List<CartDetails>>() {
                                  @Override
                                  public void onChanged(@Nullable List<CartDetails> tripsData) {
                                      TabLayout.Tab tab = TabsLayouts.tabLayout.getTabAt(2); // fourth tab
                                      View tabView = tab.getCustomView();
                                      TextView textView = tabView.findViewById(R.id.cartt);
                                      if(tripsData!=null) {
                                          textView.setVisibility(View.VISIBLE);
                                          textView.setText(String.valueOf(tripsData.size()));

                                      }else {
                                          textView.setVisibility(View.GONE);
                                      }
                                  }
                              });
                          } else if (Message.equals(" Has been added to Cart ")) {
                              Toast.makeText(getContext(), "" + getResources().getString(R.string.addedtocartexist), Toast.LENGTH_SHORT).show();
                          }
                      }

                    }
                });
            }
        });


    }

    @Override
    public void onRefresh() {
        swipe_Products.setRefreshing(false);
        GetBanners();
        GetSizes();
        GetStylest();

    }
    public void Language(){
        if(Language.isRTL()){
            Lang="he";
        }else {
            Lang="en";
        }
    }
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            TabsLayouts.Visablty = false;
        } else {

        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TabsLayouts.Visablty = false;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        TabsLayouts.Visablty = true;
    }
}


package com.shamaa.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.shamaa.myapplication.Fragments.Cart;
import com.shamaa.myapplication.Fragments.Favourit;
import com.shamaa.myapplication.Fragments.Home;
import com.shamaa.myapplication.Fragments.Products;
import com.shamaa.myapplication.Fragments.Profile;
import com.shamaa.myapplication.Language;
import com.shamaa.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TabsLayouts extends AppCompatActivity {
    public static TabLayout tabLayout;
    private ViewPager viewPager;
    View view,view1,view2,view3,view4;
    public static Boolean Visablty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_layouts);
        ButterKnife.bind(this);
        viewPager =findViewById(R.id.viewpager);
        tabLayout =findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setRotationX(180);
        RefreshTabs();
        setupTabIcons();
    }

    public void RefreshTabs(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getSupportFragmentManager(); // or 'getSupportFragmentManager();'
                int count = fm.getBackStackEntryCount();
                if(count!=0) {
                    for (int i = 0; i < count; ++i) {
                        fm.popBackStack();
                    }
                }
//                Selected_Postion(tab.getPosition());


                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupTabIcons() {
        view1 = getLayoutInflater().inflate(R.layout.icon_home, null);
        view2 = getLayoutInflater().inflate(R.layout.icon_faavourit, null);
        view3 = getLayoutInflater().inflate(R.layout.icon_cart, null);
        view4 = getLayoutInflater().inflate(R.layout.icon_profile, null);
        if(Language.isRTL()){
            tabLayout.getTabAt(3).setCustomView(view4);
            tabLayout.getTabAt(2).setCustomView(view3);
            tabLayout.getTabAt(1).setCustomView(view2);
            tabLayout.getTabAt(0).setCustomView(view1);
        }else {
            tabLayout.getTabAt(0).setCustomView(view1);
            tabLayout.getTabAt(1).setCustomView(view2);
            tabLayout.getTabAt(2).setCustomView(view3);
            tabLayout.getTabAt(3).setCustomView(view4);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(Language.isRTL()){
            adapter.addFrag(new Home(), getResources().getString(R.string.home));
            adapter.addFrag(new Favourit(), getResources().getString(R.string.favourit));
            adapter.addFrag(new Cart(), getResources().getString(R.string.cart));
            adapter.addFrag(new Profile(), getResources().getString(R.string.profile));
        }else {
            adapter.addFrag(new Home(), getResources().getString(R.string.home));
            adapter.addFrag(new Favourit(), getResources().getString(R.string.favourit));
            adapter.addFrag(new Cart(), getResources().getString(R.string.cart));
            adapter.addFrag(new Profile(), getResources().getString(R.string.profile));
        }
        viewPager.setAdapter(adapter);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        int index = tabLayout.getSelectedTabPosition();
//        if(Visablty) {
//            if (index != 0) {
//                tabLayout.getTabAt(0).select();
//            } else {
//                super.onBackPressed();
//            }
//        }else {
//            super.onBackPressed();
//        }
//    }
}

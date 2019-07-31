package com.shamaa.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shamaa.myapplication.Model.Sizes;
import com.shamaa.myapplication.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    List<Sizes> list;
    private LayoutInflater layoutInflater;
    Activity activity;

    public SpinnerAdapter(Activity activitys,List<Sizes> lists){
        this.list=lists;
        this.layoutInflater=(LayoutInflater)activitys.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity=activitys;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1=view;
        if(view==null){
            view1=layoutInflater.inflate(R.layout.textcolorspinner,null);
            TextView textView=(TextView)view1.findViewById(R.id.text);
            textView.setText(list.get(i).getSize());
        }


        return view1;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View vie=convertView;
        if(convertView==null){
            vie=layoutInflater.inflate(R.layout.dropdownspinner,null);
            TextView textView=(TextView)vie.findViewById(R.id.text);
            textView.setText(list.get(position).getSize());
        }


        return vie;
    }
}

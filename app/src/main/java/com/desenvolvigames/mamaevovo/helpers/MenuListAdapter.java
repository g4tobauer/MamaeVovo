package com.desenvolvigames.mamaevovo.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Menu;

import java.util.ArrayList;


public class MenuListAdapter extends ArrayAdapter<Menu> {

    public MenuListAdapter(Context context, ArrayList<Menu> objects) {
        super(context, R.layout.adapter_salesorderitem_spinner, objects);
    }

    @Override //don't override if you don't want the default spinner to be a two line view
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {
        if(convertView == null)
            convertView = View.inflate(getContext(),R.layout.adapter_menu_list,null);
        CheckBox MenuCheck = convertView.findViewById(R.id.chk_menu_adapter_check);
        MenuCheck.setText(getItem(position).Description);
//        MenuCheck.setChecked(getItem(position).Active);
        return convertView;
    }
}
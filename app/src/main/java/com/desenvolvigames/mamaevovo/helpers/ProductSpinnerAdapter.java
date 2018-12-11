package com.desenvolvigames.mamaevovo.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.ArrayList;

public class ProductSpinnerAdapter extends ArrayAdapter<Product> {

    public ProductSpinnerAdapter(Context context, ArrayList<Product> objects) {
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
            convertView = View.inflate(getContext(),R.layout.adapter_salesorderitem_spinner,null);
        TextView Description = convertView.findViewById(R.id.txt_salesorderitem_product_adapter_description);
        Description .setText(getItem(position).Description);
        return convertView;
    }
}

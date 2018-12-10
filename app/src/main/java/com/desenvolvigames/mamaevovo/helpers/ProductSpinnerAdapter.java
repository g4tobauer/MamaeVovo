package com.desenvolvigames.mamaevovo.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.ArrayList;

public class ProductSpinnerAdapter extends ArrayAdapter<Product> {

    public ProductSpinnerAdapter(Context context, ArrayList<Product> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return InitView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return InitView(position,convertView,parent);
    }

    private View InitView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_salesorderitem_spinner, parent, false);
            TextView txtProductDescription = convertView.findViewById(R.id.txt_salesorderitem_product_adapter_description);
            Product currentItem = getItem(position);
            if(currentItem != null)
            {
                txtProductDescription.setText(currentItem.Description);
            }
        }
        return convertView;
    }
}

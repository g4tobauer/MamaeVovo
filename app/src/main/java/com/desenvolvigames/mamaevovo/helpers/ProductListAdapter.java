package com.desenvolvigames.mamaevovo.helpers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends BaseAdapter {
    private final List<Product> lstProduct;
    private final Activity act;

    public ProductListAdapter(List<Product> lstProduct, Activity act) {
        this.lstProduct = lstProduct;
        this.act = act;
    }

    @Override
    public int getCount() {
        return lstProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return lstProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lstProduct.get(position).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.adapter_product_list, parent, false);
        Product product = lstProduct.get(position);
        TextView txtProductListAdapterDescription = view.findViewById(R.id.tx_product_list_adapter_description);
        TextView txtProductListAdapterPrice = view.findViewById(R.id.tx_product_list_adapter_price);
        TextView txtProductListAdapterUnit = view.findViewById(R.id.tx_product_list_adapter_unit);
        txtProductListAdapterDescription.setText(product.Description);
        txtProductListAdapterPrice.setText(String.format(Locale.getDefault(), "%.2f", product.Price).concat(" R$"));
        txtProductListAdapterUnit.setText(product.Unit.name());
        return view;
    }
}

package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductListAdapter;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    private ListView ltvProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent myIntent = getIntent();
        ArrayList<Product> lstProduct = myIntent.getParcelableArrayListExtra("key");

        ltvProductList = findViewById(R.id.lv_product_list);
        ltvProductList.setAdapter(new ProductListAdapter(lstProduct, ProductListActivity.this));
    }
}

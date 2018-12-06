package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent myIntent = getIntent();
        ArrayList<Product> lstProduct = myIntent.getParcelableArrayListExtra("key");

        for(Product product : lstProduct)
        {
            Long Id = product.Id;
        }
    }
}

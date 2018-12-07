package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductListAdapter;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView ltvProductList;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent myIntent = getIntent();
        ArrayList<Product> lstProduct = myIntent.getParcelableArrayListExtra("key");

        ltvProductList = findViewById(R.id.lv_product_list);
        ltvProductList.setAdapter(new ProductListAdapter(lstProduct, ProductListActivity.this));

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(ProductListActivity.this);
    }

    @Override
    public void onClick(View v)
    {
        Intent myIntent = new Intent(ProductListActivity.this, ProductActivity.class);
        myIntent.putExtra("key", "INSERT"); //Optional parameters
        ProductListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(ProductListActivity.this, MenuActivitty.class);
        ProductListActivity.this.startActivity(myIntent);
        finish();
    }
}

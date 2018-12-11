package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductListAdapter;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private ListView ltvProductList;
    private FloatingActionButton fabProductAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent myIntent = getIntent();
        ArrayList<Product> lstProduct = myIntent.getParcelableArrayListExtra("key");
        ltvProductList = findViewById(R.id.lv_product_list);
        ltvProductList.setAdapter(new ProductListAdapter(lstProduct, ProductListActivity.this));
        ltvProductList.setOnItemClickListener(ProductListActivity.this);
        ltvProductList.setOnItemLongClickListener(ProductListActivity.this);
        fabProductAdd = findViewById(R.id.fab_product_add);
        fabProductAdd.setOnClickListener(ProductListActivity.this);
    }

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(ProductListActivity.this, ProductActivity.class);
        myIntent.putExtra("key", ProductActivity.INSERT); //Optional parameters
        ProductListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(ProductListActivity.this, ProductActivity.class);
        myIntent.putExtra("key", ProductActivity.UPDATE); //Optional parameters
        myIntent.putExtra("obj", (Product)parent.getItemAtPosition(position)); //Optional parameters
        ProductListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
        Intent myIntent = new Intent(ProductListActivity.this, ProductActivity.class);
        myIntent.putExtra("key", ProductActivity.DELETE); //Optional parameters
        myIntent.putExtra("obj", (Product)arg0.getItemAtPosition(pos)); //Optional parameters
        ProductListActivity.this.startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(ProductListActivity.this, PrincipalActivitty.class);
        ProductListActivity.this.startActivity(myIntent);
        finish();
    }
}

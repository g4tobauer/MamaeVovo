package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductListAdapter;

import java.util.ArrayList;

public class SalesOrderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private FloatingActionButton salesOrderItemAdd;
    private ListView ltvProductList;
    private ArrayList<Product> lstProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorder);
        Intent myIntent = getIntent();
        ArrayList<Product> lstProductTemp = myIntent.getParcelableArrayListExtra("key");
        if(lstProductTemp != null)
            lstProduct.addAll(lstProductTemp);
        ltvProductList = findViewById(R.id.lv_item_list);
        ltvProductList.setAdapter(new ProductListAdapter(lstProduct, SalesOrderActivity.this));
        ltvProductList.setOnItemClickListener(SalesOrderActivity.this);
        ltvProductList.setOnItemLongClickListener(SalesOrderActivity.this);

        salesOrderItemAdd = findViewById(R.id.fab_salesorder_item_add);
        salesOrderItemAdd.setOnClickListener(SalesOrderActivity.this);
    }

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
//        myIntent.putExtra("key", ProductActivity.UPDATE); //Optional parameters
//        myIntent.putExtra("obj", (Product)parent.getItemAtPosition(position)); //Optional parameters
//        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
//        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
//        myIntent.putExtra("key", ProductActivity.DELETE); //Optional parameters
//        myIntent.putExtra("obj", (Product)arg0.getItemAtPosition(pos)); //Optional parameters
//        SalesOrderActivity.this.startActivity(myIntent);
//        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SalesOrderActivity.this, MenuActivitty.class);
        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }
}

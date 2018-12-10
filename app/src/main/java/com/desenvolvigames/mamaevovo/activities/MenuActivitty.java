package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.ArrayList;

public class MenuActivitty extends AppCompatActivity implements View.OnClickListener{

    private Button btnMenuCadastre;
    private Button btnSalesOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activitty);
        btnMenuCadastre = findViewById(R.id.bt_menu_cadastre);
        btnSalesOrder = findViewById(R.id.bt_menu_salesorder);
        btnMenuCadastre.setOnClickListener(MenuActivitty.this);
        btnSalesOrder.setOnClickListener(MenuActivitty.this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_menu_cadastre:
                ArrayList<Product> lstProduct = ProductBussiness.getInstance(getBaseContext()).Get(new Product());
                if(lstProduct.isEmpty()) {
                    Intent myIntent = new Intent(MenuActivitty.this, ProductActivity.class);
                    myIntent.putExtra("key", ProductActivity.INSERT); //Optional parameters
                    MenuActivitty.this.startActivity(myIntent);
                }else
                {
                    Intent myIntent = new Intent(MenuActivitty.this, ProductListActivity.class);
                    myIntent.putParcelableArrayListExtra("key", lstProduct);
                    MenuActivitty.this.startActivity(myIntent);
                }
                break;
            case R.id.bt_menu_salesorder:
                Intent myIntent = new Intent(MenuActivitty.this, SalesOrderActivity.class);
                MenuActivitty.this.startActivity(myIntent);
                break;
        }
        finish();
    }
}

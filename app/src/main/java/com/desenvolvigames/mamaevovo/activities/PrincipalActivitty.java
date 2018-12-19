package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.bussiness.SubItemBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SubItem;

import java.util.ArrayList;

public class PrincipalActivitty extends AppCompatActivity implements View.OnClickListener{

    private Button btnPrincipalCadastre;
    private Button btnSalesOrder;
    private Button btnSubItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_activitty);
        btnPrincipalCadastre = findViewById(R.id.btn_principal_product);
        btnSalesOrder = findViewById(R.id.btn_principal_salesorder);
        btnSubItem = findViewById(R.id.btn_principal_subitem);
        btnPrincipalCadastre.setOnClickListener(PrincipalActivitty.this);
        btnSalesOrder.setOnClickListener(PrincipalActivitty.this);
        btnSubItem.setOnClickListener(PrincipalActivitty.this);
    }

    @Override
    public void onClick(View v)
    {
        Intent myIntent;
        switch (v.getId())
        {
            case R.id.btn_principal_product:
                ArrayList<Product> lstProduct = ProductBussiness.getInstance(getBaseContext()).Get(new Product());
                if(lstProduct.isEmpty()) {
                    myIntent = new Intent(PrincipalActivitty.this, ProductActivity.class);
                    myIntent.setAction(ProductActivity.INSERT);
                    PrincipalActivitty.this.startActivity(myIntent);
                }else
                {
                    myIntent = new Intent(PrincipalActivitty.this, ProductListActivity.class);
                    myIntent.putParcelableArrayListExtra("key", lstProduct);
                    PrincipalActivitty.this.startActivity(myIntent);
                }
                break;
            case R.id.btn_principal_salesorder:
                myIntent = new Intent(PrincipalActivitty.this, SalesOrderActivity.class);
                PrincipalActivitty.this.startActivity(myIntent);
                break;
            case R.id.btn_principal_subitem:
                ArrayList<SubItem> lstSubItem = SubItemBussiness.getInstance(getBaseContext()).Get(new SubItem());
                if(lstSubItem.isEmpty()) {
                    myIntent = new Intent(PrincipalActivitty.this, SubItemActivity.class);
                    myIntent.setAction(SubItemActivity.INSERT);
                    PrincipalActivitty.this.startActivity(myIntent);
                }else
                {
                    myIntent = new Intent(PrincipalActivitty.this, SubItemListActivity.class);
                    myIntent.putParcelableArrayListExtra("key", lstSubItem);
                    PrincipalActivitty.this.startActivity(myIntent);
                }
                break;
        }
        finish();
    }
}

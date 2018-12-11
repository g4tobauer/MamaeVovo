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

public class PrincipalActivitty extends AppCompatActivity implements View.OnClickListener{

    private Button btnPrincipalCadastre;
    private Button btnSalesOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_activitty);
        btnPrincipalCadastre = findViewById(R.id.bt_principal_cadastre);
        btnSalesOrder = findViewById(R.id.bt_principal_salesorder);
        btnPrincipalCadastre.setOnClickListener(PrincipalActivitty.this);
        btnSalesOrder.setOnClickListener(PrincipalActivitty.this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_principal_cadastre:
                ArrayList<Product> lstProduct = ProductBussiness.getInstance(getBaseContext()).Get(new Product());
                if(lstProduct.isEmpty()) {
                    Intent myIntent = new Intent(PrincipalActivitty.this, ProductActivity.class);
                    myIntent.putExtra("key", ProductActivity.INSERT); //Optional parameters
                    PrincipalActivitty.this.startActivity(myIntent);
                }else
                {
                    Intent myIntent = new Intent(PrincipalActivitty.this, ProductListActivity.class);
                    myIntent.putParcelableArrayListExtra("key", lstProduct);
                    PrincipalActivitty.this.startActivity(myIntent);
                }
                break;
            case R.id.bt_principal_salesorder:
                Intent myIntent = new Intent(PrincipalActivitty.this, SalesOrderActivity.class);
                PrincipalActivitty.this.startActivity(myIntent);
                break;
        }
        finish();
    }
}

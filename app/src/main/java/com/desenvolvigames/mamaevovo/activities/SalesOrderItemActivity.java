package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;

public class SalesOrderItemActivity extends AppCompatActivity {

    private Spinner salesOrderitemProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorderitem);
        salesOrderitemProducts = findViewById(R.id.spn_salesorderitem_products);
        salesOrderitemProducts.setAdapter(new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product())));
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SalesOrderItemActivity.this, SalesOrderActivity.class);
        SalesOrderItemActivity.this.startActivity(myIntent);
        finish();
    }
}

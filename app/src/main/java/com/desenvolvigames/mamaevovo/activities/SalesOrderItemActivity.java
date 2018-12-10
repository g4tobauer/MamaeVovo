package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;

public class SalesOrderItemActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spnSalesOrderitemProducts;
    private Button btnsalesOrderitemConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorderitem);
        spnSalesOrderitemProducts = findViewById(R.id.spn_salesorderitem_products);
        btnsalesOrderitemConfirm = findViewById(R.id.btn_salesorderitem_confirm);
        spnSalesOrderitemProducts.setAdapter(new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product())));
        btnsalesOrderitemConfirm.setOnClickListener(SalesOrderItemActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        intent.putExtra("result","teste");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}

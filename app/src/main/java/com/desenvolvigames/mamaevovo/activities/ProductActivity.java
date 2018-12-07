package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    private Button btnProductConfirm;
    private EditText edtProductDescription;
    private EditText edtProductPrice;
    private EditText edtProductObs;
    private RadioGroup rdgProductUnit;
    private String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        action = intent.getStringExtra("key"); //if it's a string you stored.
        btnProductConfirm = findViewById(R.id.bt_product_confirm);
        edtProductDescription = findViewById(R.id.ed_product_description);
        edtProductPrice = findViewById(R.id.ed_product_price);
        edtProductObs = findViewById(R.id.ed_product_obs);
        rdgProductUnit = findViewById(R.id.rg_product_unit);
        btnProductConfirm.setOnClickListener(ProductActivity.this);
        InitFields(intent);
    }

    private void InitFields(Intent intent)
    {
        switch (action)
        {
            case INSERT:
                break;
            case UPDATE:
                Product prod  = intent.getParcelableExtra("obj"); //if it's a string you stored.
                break;
            case DELETE:
                break;
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.bt_product_confirm:
                String strTemp;
                Product product = new Product();
                strTemp = edtProductDescription.getText().toString();
                product.Description = strTemp.isEmpty() ? null : strTemp;

                strTemp = edtProductPrice.getText().toString();
                product.Price = strTemp.isEmpty() ? null : Double.parseDouble(edtProductPrice.getText().toString());
                int selectedId = rdgProductUnit.getCheckedRadioButtonId();
                switch (selectedId)
                {
                    case R.id.rd_product_un:
                        product.Unit = ProductUnitEnum.UN;
                        break;
                    case R.id.rd_product_kg:
                        product.Unit = ProductUnitEnum.KG;
                        break;
                        default:
                            product.Unit = null;
                            break;
                }
                strTemp = edtProductObs.getText().toString();
                product.Obs = strTemp.isEmpty() ? null : strTemp;

                switch (action)
                {
                    case INSERT:
                        Insert(product, v);
                        break;
                    case UPDATE:
                        Update(product, v);
                        break;
                    case DELETE:
                        Delete(product, v);
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ArrayList<Product> lstProduct = ProductBussiness.getInstance(getBaseContext()).Get(new Product());
        if(lstProduct.size() > 0) {
            Intent myIntent = new Intent(ProductActivity.this, ProductListActivity.class);
            myIntent.putParcelableArrayListExtra("key", lstProduct);
            ProductActivity.this.startActivity(myIntent);
        }else
        {
            Intent myIntent = new Intent(ProductActivity.this, MenuActivitty.class);
            ProductActivity.this.startActivity(myIntent);
        }
        finish();
    }

    private void Insert(Product product, View v){
        Handler handler = new Handler();
        Product resultProduct = ProductBussiness.getInstance(getBaseContext()).Insert(product);
        if(resultProduct != null && product.Description != null && product.Description.equals(resultProduct.Description))
        {
            Snackbar.make(v, R.string.product_insert_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Product> lstProduct = ProductBussiness.getInstance(getBaseContext()).Get(new Product());
                    Intent myIntent = new Intent(ProductActivity.this, ProductListActivity.class);
                    myIntent.putParcelableArrayListExtra("key", lstProduct);
                    ProductActivity.this.startActivity(myIntent);
                    finish();
                }
            }, 2000); // 5000ms delay
        }
        else
        {
            Snackbar.make(v, R.string.product_insert_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent myIntent = new Intent(ProductActivity.this, MenuActivitty.class);
                    ProductActivity.this.startActivity(myIntent);
                    finish();
                }
            }, 2000); // 5000ms delay
        }
    }

    private void Delete(Product product, View v){}

    private void Update(Product product, View v){}

}

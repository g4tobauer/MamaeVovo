package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

import java.util.ArrayList;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, Runnable{

    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    private Product product;
    private Button btnProductConfirm;
    private EditText edtProductDescription;
    private EditText edtProductPrice;
    private EditText edtProductObs;
    private CheckBox chkProductUsaSubItens;
    private RadioGroup rdgProductUnit;
    private RadioButton rbProductKg;
    private RadioButton rbProductUn;
    private String action;
    private boolean actionIsOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        action = intent.getStringExtra("key"); //if it's a string you stored.
        edtProductDescription = findViewById(R.id.ed_product_description);
        edtProductPrice = findViewById(R.id.ed_product_price);
        edtProductObs = findViewById(R.id.ed_product_obs);
        chkProductUsaSubItens = findViewById(R.id.chk_usa_subitens);
        rdgProductUnit = findViewById(R.id.rg_product_unit);
        rbProductKg = findViewById(R.id.rb_product_kg);
        rbProductUn = findViewById(R.id.rb_product_un);

        btnProductConfirm = findViewById(R.id.bt_product_confirm);
        btnProductConfirm.setOnClickListener(ProductActivity.this);

        InitFields(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.bt_product_confirm:
                String strTemp;
                strTemp = edtProductDescription.getText().toString();
                product.Description = strTemp.isEmpty() ? null : strTemp;

                strTemp = edtProductPrice.getText().toString();
                product.Price = strTemp.isEmpty() ? null : Double.parseDouble(edtProductPrice.getText().toString().replace(",", "."));
                int selectedId = rdgProductUnit.getCheckedRadioButtonId();
                switch (selectedId)
                {
                    case R.id.rb_product_un:
                        product.Unit = ProductUnitEnum.UN;
                        break;
                    case R.id.rb_product_kg:
                        product.Unit = ProductUnitEnum.KG;
                        break;
                        default:
                            product.Unit = null;
                            break;
                }
                product.UsaSubItens = chkProductUsaSubItens.isChecked();
                strTemp = edtProductObs.getText().toString();
                product.Obs = strTemp.isEmpty() ? null : strTemp;

                switch (action)
                {
                    case INSERT:
                        Insert(v);
                        break;
                    case UPDATE:
                        Update(v);
                        break;
                    case DELETE:
                        Delete(v);
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
            Intent myIntent = new Intent(ProductActivity.this, PrincipalActivitty.class);
            ProductActivity.this.startActivity(myIntent);
        }
        finish();
    }

    @Override
    public void run(){
        ArrayList<Product> lstProduct = ProductBussiness.getInstance(getBaseContext()).Get(new Product());
        if(actionIsOk && !lstProduct.isEmpty())
        {
            Intent myIntent = new Intent(ProductActivity.this, ProductListActivity.class);
            myIntent.putParcelableArrayListExtra("key", lstProduct);
            ProductActivity.this.startActivity(myIntent);
            finish();

        }else
        {
            Intent myIntent = new Intent(ProductActivity.this, PrincipalActivitty.class);
            ProductActivity.this.startActivity(myIntent);
            finish();
        }
    }

    private void InitFields(Intent intent){
        switch (action)
        {
            case INSERT:
                product = new Product();
                EnableFields(true);
                btnProductConfirm.setText(R.string.insert);
                break;
            case UPDATE:
                FillFields(intent);
                EnableFields(true);
                btnProductConfirm.setText(R.string.update);
                break;
            case DELETE:
                FillFields(intent);
                EnableFields(false);
                btnProductConfirm.setText(R.string.delete);
                break;
        }
    }

    private void FillFields(Intent intent){
        product  = intent.getParcelableExtra("obj"); //if it's a string you stored.
        edtProductDescription.setText(product.Description);
        edtProductPrice.setText(String.format(Locale.getDefault(), "%.2f", product.Price == null ? 0 : product.Price));
        edtProductObs.setText(product.Obs);
        chkProductUsaSubItens.setChecked(product.UsaSubItens);
        switch (product.Unit)
        {
            case UN:
                rdgProductUnit.check(R.id.rb_product_un);
                break;
            case KG:
                rdgProductUnit.check(R.id.rb_product_kg);
                break;
            default:
                rdgProductUnit.clearCheck();
                break;
        }
    }

    private void EnableFields(boolean value){
        edtProductDescription.setEnabled(value);
        edtProductPrice.setEnabled(value);
        edtProductObs.setEnabled(value);
        rdgProductUnit.setEnabled(value);
        chkProductUsaSubItens.setEnabled(value);
        rbProductKg.setEnabled(value);
        rbProductUn.setEnabled(value);
    }

    private void Insert(View v){
        Handler handler = new Handler();
        Product resultProduct = ProductBussiness.getInstance(getBaseContext()).Insert(product);
        if(resultProduct != null && product.Description != null && product.Description.equals(resultProduct.Description))
        {
            actionIsOk = true;
            Snackbar.make(v, R.string.product_insert_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(ProductActivity.this, 2000); // 5000ms delay
        }
        else
        {
            actionIsOk = false;
            Snackbar.make(v, R.string.product_insert_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(ProductActivity.this, 2000); // 5000ms delay
        }
    }

    private void Update(View v){
        Handler handler = new Handler();
        if(ProductBussiness.getInstance(getBaseContext()).Update(product))
        {
            actionIsOk = true;
            Snackbar.make(v, R.string.product_update_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(ProductActivity.this, 2000); // 5000ms delay
        }
        else
        {
            actionIsOk = false;
            Snackbar.make(v, R.string.product_update_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(ProductActivity.this, 2000); // 5000ms delay
        }
    }

    private void Delete(View v){
        Handler handler = new Handler();
        if(ProductBussiness.getInstance(getBaseContext()).Delete(product))
        {
            actionIsOk = true;
            Snackbar.make(v, R.string.product_delete_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(ProductActivity.this, 2000); // 5000ms delay
        }
        else
        {
            actionIsOk = false;
            Snackbar.make(v, R.string.product_delete_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(ProductActivity.this, 2000); // 5000ms delay
        }
    }
}

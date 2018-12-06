package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnProductConfirm;
    private EditText edtProductDescription;
    private EditText edtProductPrice;
    private EditText edtProductObs;
    private RadioGroup rdgProductUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.

        btnProductConfirm = findViewById(R.id.bt_product_confirm);
        edtProductDescription = findViewById(R.id.ed_product_description);
        edtProductPrice = findViewById(R.id.ed_product_price);
        edtProductObs = findViewById(R.id.ed_product_obs);
        rdgProductUnit = findViewById(R.id.rg_product_unit);

        btnProductConfirm.setOnClickListener(ProductActivity.this);
    }
    @Override
    public void onClick(View v)
    {
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
                if(Save(product))
                    Toast.makeText(getBaseContext(),R.string.product_insert_ok,Toast.LENGTH_SHORT);
                else
                    Toast.makeText(getBaseContext(),R.string.product_insert_fail,Toast.LENGTH_SHORT);
                break;
        }
    }

    private boolean Save(Product product)
    {
        Product newProduct = ProductBussiness.getInstance(getBaseContext()).Insert(product);
        return product.Description.equals(newProduct.Description);
    }
}

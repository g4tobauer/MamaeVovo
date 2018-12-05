package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnProductConfirm;
    EditText edtProductDescription;
    EditText edtProductPrice;
    EditText edtProductObs;
    RadioGroup rdgProductUnit;

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

//        Product product = new Product();
//        product.Id = 1L;
//        product.Description = "teste";
//        product.Unit = ProductUnitEnum.KG;
//        product.Price = 20D;
//
//        product = ProductBussiness.getInstance(getBaseContext()).Update(product);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_product_confirm:
                Product product = new Product();
                product.Description = edtProductDescription.getText().toString();
                product.Price = Double.parseDouble(edtProductPrice.getText().toString());
                int selectedId = rdgProductUnit.getCheckedRadioButtonId();
                switch (selectedId)
                {
                    case R.id.rd_product_un:
                        product.Unit = ProductUnitEnum.UN;
                        break;
                    case R.id.rd_product_kg:
                        product.Unit = ProductUnitEnum.KG;
                        break;
                }
                break;
        }
    }
}

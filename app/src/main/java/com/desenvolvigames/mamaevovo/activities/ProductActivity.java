package com.desenvolvigames.mamaevovo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Product product = new Product();
        product.Id = 1L;
        product.Description = "teste";
        product.Unit = ProductUnitEnum.KG;
        product.Price = 20D;

        product = ProductBussiness.getInstance(getBaseContext()).Update(product);
    }
}

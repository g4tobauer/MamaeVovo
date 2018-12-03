package com.desenvolvigames.mamaevovo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.desenvolvigames.mamaevovo.dataAccess.ProductDataAccess;
import com.desenvolvigames.mamaevovo.entities.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product product = new Product();
        product.Id = 7L;
        product.Description = "biriu";
        product = ProductDataAccess.getInstance(getBaseContext()).Insert(product);
        product.Description = "Cossovo";
        ProductDataAccess.getInstance(getBaseContext()).Update(product);
        ProductDataAccess.getInstance(getBaseContext()).Delete(new Product());
        ProductDataAccess.getInstance(getBaseContext()).Get(new Product());
    }
}


//https://www.androidpro.com.br/blog/armazenamento-de-dados/sqlite/
package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Menu;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SalesOrderItemActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spnSalesOrderitemProducts;
    private Button btnsalesOrderitemConfirm;
    private ListView lstMenuCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorderitem);
        spnSalesOrderitemProducts = findViewById(R.id.spn_salesorderitem_products);
        lstMenuCheck = findViewById(R.id.lst_menu_check);
        btnsalesOrderitemConfirm = findViewById(R.id.btn_salesorderitem_confirm);
//        spnSalesOrderitemProducts.setAdapter(new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product())));



        ProductSpinnerAdapter adapter =
                new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product()));
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spnSalesOrderitemProducts.setAdapter(adapter);

//        lstMenuCheck.setAdapter(new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product())));
        btnsalesOrderitemConfirm.setOnClickListener(SalesOrderItemActivity.this);
        teste();
    }

    private void teste()
    {
        ArrayList<Menu> lstMenu = new ArrayList<>();
        Menu menu = new Menu();
        menu.Id = 1L;
        menu.Description = "Arroz";
        menu.Active = false;
        lstMenu.add(menu);

        menu = new Menu();
        menu.Id = 2L;
        menu.Description = "Feijão";
        menu.Active = false;
        lstMenu.add(menu);

        menu = new Menu();
        menu.Id = 3L;
        menu.Description = "Frango";
        menu.Active = false;
        lstMenu.add(menu);
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        SalesOrderItem teste = new SalesOrderItem();
        intent.putExtra("result", teste);
//        intent.putExtra("result","teste");
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

package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.MenuBussiness;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Menu;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;

public class SalesOrderItemActivity extends ListActivity implements View.OnClickListener {

    private Spinner spnSalesOrderitemProducts;
    private Button btnsalesOrderitemConfirm;
    private ListView lstMenuCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorderitem);

        lstMenuCheck = getListView();
        lstMenuCheck.setChoiceMode(lstMenuCheck.CHOICE_MODE_MULTIPLE);
        lstMenuCheck.setTextFilterEnabled(true);
        setListAdapter(new ArrayAdapter<>(SalesOrderItemActivity.this, android.R.layout.simple_list_item_checked, MenuBussiness.getInstance(getBaseContext()).Get(new Menu())));

        ArrayAdapter adapter = new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product()));
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnSalesOrderitemProducts = findViewById(R.id.spn_salesorderitem_products);
        spnSalesOrderitemProducts.setAdapter(adapter);

        btnsalesOrderitemConfirm = findViewById(R.id.btn_salesorderitem_confirm);
        btnsalesOrderitemConfirm.setOnClickListener(SalesOrderItemActivity.this);
    }

    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        CheckedTextView item = (CheckedTextView) v;
        Menu menu = (Menu)parent.getItemAtPosition(position);
        Toast.makeText(SalesOrderItemActivity.this, menu.Description + " checked : " +
                item.isChecked(), Toast.LENGTH_SHORT).show();
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

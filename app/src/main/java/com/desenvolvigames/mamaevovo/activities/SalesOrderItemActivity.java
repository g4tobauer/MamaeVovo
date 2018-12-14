package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.MenuBussiness;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Menu;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;

import java.util.ArrayList;

public class SalesOrderItemActivity extends ListActivity implements View.OnClickListener {

    private Spinner spnSalesOrderitemProducts;
    private Button btnsalesOrderitemConfirm;
    private ListView lstMenuCheck;
    private ArrayList<Menu> lstMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorderitem);

        lstMenuCheck = getListView();
        lstMenuCheck.setChoiceMode(lstMenuCheck.CHOICE_MODE_MULTIPLE);
        lstMenuCheck.setTextFilterEnabled(true);
        lstMenu = MenuBussiness.getInstance(getBaseContext()).Get(new Menu());
        setListAdapter(new ArrayAdapter<>(SalesOrderItemActivity.this, android.R.layout.simple_list_item_checked, lstMenu));

        ProductSpinnerAdapter adapter = new ProductSpinnerAdapter(getBaseContext(), ProductBussiness.getInstance(getBaseContext()).Get(new Product()));
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnSalesOrderitemProducts = findViewById(R.id.spn_salesorderitem_products);
        spnSalesOrderitemProducts.setAdapter(adapter);

        btnsalesOrderitemConfirm = findViewById(R.id.btn_salesorderitem_confirm);
        btnsalesOrderitemConfirm.setOnClickListener(SalesOrderItemActivity.this);
//        lstMenuCheck.setItemChecked();
        ManageAction();
    }

    private void ManageAction()
    {
        Intent intent = getIntent();
        switch(intent.getAction())
        {
            case "UPDATE":
                SalesOrderItem salesOrderItem = intent.getParcelableExtra("key");
                ProductSpinnerAdapter adapter = ((ProductSpinnerAdapter)spnSalesOrderitemProducts.getAdapter());
                for(Product product : adapter.getProducts())
                {
                    if(salesOrderItem.Product.Id == product.Id)
                    {
                        spnSalesOrderitemProducts.setSelection(adapter.getPosition(product));
                    }
                }

                for(Menu menu : salesOrderItem.MenuItem)
                {
                    if(menu.Active)
                    {
                        for (int i = 0; i < lstMenuCheck.getAdapter().getCount(); i++) {
                            Menu menuInner = (Menu)lstMenuCheck.getAdapter().getItem(i);
                            if(menu.Id == menuInner.Id)
                            {
                                menuInner.Active = true;
                                lstMenuCheck.setItemChecked(i, menuInner.Active);
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }


    @Override
    public void onListItemClick(ListView parent, View v, int position,long id){
        CheckedTextView item = (CheckedTextView) v;
        Menu menu = (Menu)parent.getItemAtPosition(position);
        menu.Active = item.isChecked();
//        Toast.makeText(SalesOrderItemActivity.this, menu.Description + " checked : " +
//                menu.Active, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        SalesOrderItem salesOrderItem = new SalesOrderItem();
        ArrayList<Menu> lstMenuTemp = new ArrayList<>();
        for(Menu menu : lstMenu)
        {
            if(menu.Active)
                lstMenuTemp.add(menu);
        }
        salesOrderItem.Product = (Product) spnSalesOrderitemProducts.getSelectedItem();
        salesOrderItem.MenuItem = lstMenuTemp;
        intent.putExtra("result", salesOrderItem);
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

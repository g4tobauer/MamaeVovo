package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.MenuBussiness;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.Menu;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

import java.util.ArrayList;

import static com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum.*;

public class SalesOrderItemActivity extends ListActivity implements View.OnClickListener {

    private Spinner spnSalesOrderitemProducts;
    private Button btnsalesOrderitemConfirm;
    private ListView lstMenuCheck;
    private ArrayList<Menu> lstMenu;
    private SalesOrderItem salesOrderItem;

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
        if(salesOrderItem == null)
            salesOrderItem = new SalesOrderItem();
        ArrayList<Menu> lstMenuTemp = new ArrayList<>();
        salesOrderItem.MenuItem.clear();
        for(Menu menu : lstMenu)
        {
            if(menu.Active)
                lstMenuTemp.add(menu);
        }
        salesOrderItem.Product = (Product) spnSalesOrderitemProducts.getSelectedItem();
        salesOrderItem.MenuItem = lstMenuTemp;
        InputConditions();
    }

    private void InputConditions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SalesOrderItemActivity.this);
        // Set up the input
        final EditText input = new EditText(SalesOrderItemActivity.this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

        switch (salesOrderItem.Product.Unit)
        {
            case UN:
                builder.setTitle("Unidades");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case KG:
                builder.setTitle("Kg/s");
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
        }
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().isEmpty())
                    dialog.cancel();
                else {
                    salesOrderItem.Quantidade = Double.parseDouble(input.getText().toString());
                    Intent intent = getIntent();
                    intent.putExtra("result", salesOrderItem);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertToShow = builder.create();
        alertToShow.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alertToShow.show();
//        builder.show();
    }


    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    private void ManageAction(){
        Intent intent = getIntent();
        switch(intent.getAction())
        {
            case "UPDATE":
                salesOrderItem = intent.getParcelableExtra("key");
                ProductSpinnerAdapter adapter = ((ProductSpinnerAdapter)spnSalesOrderitemProducts.getAdapter());
                for(Product product : adapter.getProducts())
                {
                    if(salesOrderItem.Product.Id == product.Id)
                    {
                        spnSalesOrderitemProducts.setSelection(adapter.getPosition(product));
                        break;
                    }
                }

                for (int i = 0; i < lstMenuCheck.getAdapter().getCount(); i++)
                {
                    Menu menuTemp = (Menu)lstMenuCheck.getAdapter().getItem(i);
                    menuTemp.Active = false;
                    lstMenuCheck.setItemChecked(i, menuTemp.Active);
                    for(Menu menu : salesOrderItem.MenuItem)
                    {
                        if(menu.Id == menuTemp.Id)
                        {
                            menuTemp.Active = menu.Active;
                            lstMenuCheck.setItemChecked(i, menuTemp.Active);
                            break;
                        }
                    }
                }
                break;
            default:
                for (int i = 0; i < lstMenuCheck.getAdapter().getCount(); i++)
                {
                    Menu menu = (Menu)lstMenuCheck.getAdapter().getItem(i);
                    lstMenuCheck.setItemChecked(i, menu.Active);
                }
                break;
        }
        ((ArrayAdapter) lstMenuCheck.getAdapter()).notifyDataSetChanged();
    }

}

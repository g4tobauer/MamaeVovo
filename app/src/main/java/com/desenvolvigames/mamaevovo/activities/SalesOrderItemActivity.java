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
import com.desenvolvigames.mamaevovo.bussiness.SubItemBussiness;
import com.desenvolvigames.mamaevovo.bussiness.ProductBussiness;
import com.desenvolvigames.mamaevovo.entities.SubItem;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;
import com.desenvolvigames.mamaevovo.helpers.ProductSpinnerAdapter;

import java.util.ArrayList;

public class SalesOrderItemActivity extends ListActivity implements View.OnClickListener {

    private Spinner spnSalesOrderitemProducts;
    private Button btnsalesOrderitemConfirm;
    private ListView lstMenuCheck;
    private ArrayList<SubItem> lstSubItems;
    private SalesOrderItem salesOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorderitem);

        lstMenuCheck = getListView();
        lstMenuCheck.setChoiceMode(lstMenuCheck.CHOICE_MODE_MULTIPLE);
        lstMenuCheck.setTextFilterEnabled(true);
        lstSubItems = SubItemBussiness.getInstance(getBaseContext()).Get(new SubItem());
        setListAdapter(new ArrayAdapter<>(SalesOrderItemActivity.this, android.R.layout.simple_list_item_checked, lstSubItems));

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
        SubItem subItem = (SubItem)parent.getItemAtPosition(position);
        subItem.Active = item.isChecked();
//        Toast.makeText(SalesOrderItemActivity.this, subItem.Description + " checked : " +
//                subItem.Active, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(salesOrderItem == null)
            salesOrderItem = new SalesOrderItem();
        ArrayList<SubItem> lstSubItemTemp = new ArrayList<>();
        salesOrderItem.subItemItem.clear();
        for(SubItem subItem : lstSubItems)
        {
            if(subItem.Active)
                lstSubItemTemp.add(subItem);
        }
        salesOrderItem.Product = (Product) spnSalesOrderitemProducts.getSelectedItem();
        salesOrderItem.subItemItem = lstSubItemTemp;
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
                    SubItem subItemTemp = (SubItem)lstMenuCheck.getAdapter().getItem(i);
                    subItemTemp.Active = false;
                    lstMenuCheck.setItemChecked(i, subItemTemp.Active);
                    for(SubItem subItem : salesOrderItem.subItemItem)
                    {
                        if(subItem.Id == subItemTemp.Id)
                        {
                            subItemTemp.Active = subItem.Active;
                            lstMenuCheck.setItemChecked(i, subItemTemp.Active);
                            break;
                        }
                    }
                }
                break;
            default:
                for (int i = 0; i < lstMenuCheck.getAdapter().getCount(); i++)
                {
                    SubItem subItem = (SubItem)lstMenuCheck.getAdapter().getItem(i);
                    lstMenuCheck.setItemChecked(i, subItem.Active);
                }
                break;
        }
        ((ArrayAdapter) lstMenuCheck.getAdapter()).notifyDataSetChanged();
    }

}

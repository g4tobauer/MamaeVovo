package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.SalesOrderBussiness;
import com.desenvolvigames.mamaevovo.entities.SalesOrder;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;

import java.util.ArrayList;

public class SalesOrderActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{

    private SalesOrder salesOrder;
    private FloatingActionButton salesOrderItemAdd;
    private ListView ltvSalesOrderItem;
    private ArrayList<SalesOrderItem> lstSalesOrderItem;
    private SalesOrderItem salesOrderItemOldTemp;

    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorder);
        lstSalesOrderItem = new ArrayList<>();
        InitFields();
        ltvSalesOrderItem = getListView();
        ltvSalesOrderItem.setChoiceMode(ltvSalesOrderItem.CHOICE_MODE_SINGLE);
        ltvSalesOrderItem.setTextFilterEnabled(true);
        ltvSalesOrderItem.setOnItemLongClickListener(SalesOrderActivity.this);
        setListAdapter(new ArrayAdapter<>(SalesOrderActivity.this, android.R.layout.simple_list_item_1, lstSalesOrderItem));

        salesOrderItemAdd = findViewById(R.id.fab_salesorder_item_add);
        salesOrderItemAdd.setOnClickListener(SalesOrderActivity.this);
    }

    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        salesOrderItemOldTemp = (SalesOrderItem)parent.getItemAtPosition(position);
        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
        myIntent.putExtra("key", salesOrderItemOldTemp);
        myIntent.setAction(UPDATE);
        SalesOrderActivity.this.startActivityForResult(myIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            SalesOrderItem salesOrderItem;
            switch (requestCode)
            {
                case 1:
                    salesOrderItem = data.getParcelableExtra("result");
                    if(lstSalesOrderItem.size() == 0) salesOrderItem.Id = 0L;
                    else
                    {
                        Long id = lstSalesOrderItem.get(lstSalesOrderItem.size()-1).Id;
                        salesOrderItem.Id = id+1;
                    }
                    lstSalesOrderItem.add(salesOrderItem);
                    break;
                case 2:
                    salesOrderItem = data.getParcelableExtra("result");
                    lstSalesOrderItem.set(lstSalesOrderItem.indexOf(salesOrderItemOldTemp), salesOrderItem);
                    salesOrderItemOldTemp = null;
                    break;
            }
            ((ArrayAdapter) ltvSalesOrderItem.getAdapter()).notifyDataSetChanged();
        }else
        if (resultCode == Activity.RESULT_CANCELED)
        {
            //Write your code if there's no result
        }
    }//onActivityResult

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
        myIntent.setAction(INSERT);
        SalesOrderActivity.this.startActivityForResult(myIntent, 1);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
        final SalesOrderItem salesOrderItem = (SalesOrderItem)arg0.getItemAtPosition(pos);

        CharSequence options[] = new CharSequence[]{"Sim", "Não"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseja deletar o item " + salesOrderItem.Product.Description + " ?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    lstSalesOrderItem.remove(salesOrderItem);
                    ((ArrayAdapter)ltvSalesOrderItem.getAdapter()).notifyDataSetChanged();
                }
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(lstSalesOrderItem.isEmpty()) {
            FinishActivity();
        }else
        {
            CharSequence options[] = new CharSequence[]{"Sim", "Não", "Cancelar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Deseja concluir este Pedido ?");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case 0:
                            switch(getIntent().getAction())
                            {
                                case INSERT:
                                    salesOrder.IdDate = 1L;
                                    salesOrder.SalesOrderItem = lstSalesOrderItem;
                                    SalesOrderBussiness.getInstance(SalesOrderActivity.this).Insert(salesOrder);
                                    break;
                                case UPDATE:
                                    SalesOrderBussiness.getInstance(SalesOrderActivity.this).Update(salesOrder);
                                    break;
                            }
                            FinishActivity();
                            break;
                        case 1:
                            FinishActivity();
                            break;
                        default:
                            break;
                    }
                }
            });
            builder.show();
        }
    }

    private void InitFields() {
        switch(getIntent().getAction())
        {
            case INSERT:
                salesOrder = new SalesOrder();
                break;
            case UPDATE:
                salesOrder  = getIntent().getParcelableExtra("key"); //if it's a string you stored.
                lstSalesOrderItem.addAll(salesOrder.SalesOrderItem);
                break;
        }
    }

    private void FinishActivity(){
        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderListActivity.class);
        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }
}

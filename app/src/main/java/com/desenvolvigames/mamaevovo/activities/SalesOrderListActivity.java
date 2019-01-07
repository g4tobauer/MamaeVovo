package com.desenvolvigames.mamaevovo.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.SalesOrderBussiness;
import com.desenvolvigames.mamaevovo.entities.SalesOrder;

import java.util.ArrayList;

public class SalesOrderListActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{
    private FloatingActionButton salesOrderAdd;
    private ListView ltvSalesOrder;
    private ArrayList<SalesOrder> lstSalesOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_list);
        lstSalesOrder = new ArrayList<>();
        lstSalesOrder.addAll(SalesOrderBussiness.getInstance(getApplicationContext()).Get(new SalesOrder()));
        ltvSalesOrder = getListView();
        ltvSalesOrder.setChoiceMode(ltvSalesOrder.CHOICE_MODE_SINGLE);
        ltvSalesOrder.setTextFilterEnabled(true);
        ltvSalesOrder.setOnItemLongClickListener(SalesOrderListActivity.this);
        setListAdapter(new ArrayAdapter<>(SalesOrderListActivity.this, android.R.layout.simple_list_item_1, lstSalesOrder));
        salesOrderAdd = findViewById(R.id.fab_salesorder_add);
        salesOrderAdd.setOnClickListener(SalesOrderListActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(SalesOrderListActivity.this, SalesOrderActivity.class);
        myIntent.setAction(SalesOrderActivity.INSERT);
        SalesOrderListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        Intent myIntent = new Intent(SalesOrderListActivity.this, SalesOrderActivity.class);
        myIntent.setAction(SalesOrderActivity.UPDATE);
        myIntent.putExtra("key", (SalesOrder)parent.getItemAtPosition(position)); //Optional parameters
        SalesOrderListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SalesOrderListActivity.this, PrincipalActivitty.class);
        SalesOrderListActivity.this.startActivity(myIntent);
        finish();
    }
}

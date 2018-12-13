package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;

import java.util.ArrayList;

public class SalesOrderActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private FloatingActionButton salesOrderItemAdd;
    private ListView ltvSalesOrderItem;
    private ArrayList<SalesOrderItem> lstSalesOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorder);
        Intent myIntent = getIntent();
//        ArrayList<Product> lstProductTemp = myIntent.getParcelableArrayListExtra("key");
//        if(lstProductTemp != null) lstProduct.addAll(lstProductTemp);
//        else lstProduct = new ArrayList<>();
        lstSalesOrderItem = new ArrayList<>();
        ltvSalesOrderItem = getListView();
        ltvSalesOrderItem.setChoiceMode(ltvSalesOrderItem.CHOICE_MODE_SINGLE);
        ltvSalesOrderItem.setTextFilterEnabled(true);
        setListAdapter(new ArrayAdapter<>(SalesOrderActivity.this, android.R.layout.simple_list_item_1, lstSalesOrderItem));

        salesOrderItemAdd = findViewById(R.id.fab_salesorder_item_add);
        salesOrderItemAdd.setOnClickListener(SalesOrderActivity.this);
    }

    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        SalesOrderItem salesOrderItem = (SalesOrderItem)parent.getItemAtPosition(position);
        Toast.makeText(SalesOrderActivity.this, salesOrderItem.toString() , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                SalesOrderItem salesOrderItem = data.getParcelableExtra("result");
                lstSalesOrderItem.add(salesOrderItem);
                ((ArrayAdapter)ltvSalesOrderItem.getAdapter()).notifyDataSetChanged();
            }else
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
        SalesOrderActivity.this.startActivityForResult(myIntent, 1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
//        myIntent.putExtra("key", ProductActivity.UPDATE); //Optional parameters
//        myIntent.putExtra("obj", (Product)parent.getItemAtPosition(position)); //Optional parameters
//        SalesOrderActivity.this.startActivity(myIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
//        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
//        myIntent.putExtra("key", ProductActivity.DELETE); //Optional parameters
//        myIntent.putExtra("obj", (Product)arg0.getItemAtPosition(pos)); //Optional parameters
//        SalesOrderActivity.this.startActivity(myIntent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SalesOrderActivity.this, PrincipalActivitty.class);
        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }
}

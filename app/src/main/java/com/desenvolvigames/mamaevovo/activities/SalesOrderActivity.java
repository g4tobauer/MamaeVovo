package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.desenvolvigames.mamaevovo.R;

public class SalesOrderActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton salesOrderItemAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorder);
        salesOrderItemAdd = findViewById(R.id.fab_salesorder_item_add);
        salesOrderItemAdd.setOnClickListener(SalesOrderActivity.this);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SalesOrderActivity.this, MenuActivitty.class);
        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(SalesOrderActivity.this, SalesOrderItemActivity.class);
        SalesOrderActivity.this.startActivity(myIntent);
        finish();
    }
}

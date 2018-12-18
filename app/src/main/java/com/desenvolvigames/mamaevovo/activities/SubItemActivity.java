package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.SubItemBussiness;
import com.desenvolvigames.mamaevovo.entities.SubItem;

import java.util.ArrayList;

public class SubItemActivity extends AppCompatActivity implements View.OnClickListener, Runnable{

    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    private SubItem subItem;
    private EditText edtSubitemDescription;
    private CheckBox chkSubitemActive;
    private Button btnSubitemConfirm;
    private String action;
    private boolean actionIsOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subitem);
        Intent intent = getIntent();
        action = intent.getStringExtra("key"); //if it's a string you stored.
        edtSubitemDescription = findViewById(R.id.edt_subitem_description);
        chkSubitemActive = findViewById(R.id.chk_subitem_active);
        btnSubitemConfirm = findViewById(R.id.btn_subitem_confirm);
        btnSubitemConfirm.setOnClickListener(SubItemActivity.this);
        InitFields(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.btn_subitem_confirm:
                String strTemp;
                strTemp = edtSubitemDescription.getText().toString();
                subItem.Description = strTemp.isEmpty() ? null : strTemp;
                subItem.Active = chkSubitemActive.isChecked();
                switch (action)
                {
                    case INSERT:
                        Insert(v);
                        break;
                    case UPDATE:
                        Update(v);
                        break;
                    case DELETE:
                        Delete(v);
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ArrayList<SubItem> lstSubItem = SubItemBussiness.getInstance(getBaseContext()).Get(new SubItem());
        if(lstSubItem.size() > 0) {
            Intent myIntent = new Intent(SubItemActivity.this, SubItemListActivity.class);
            myIntent.putParcelableArrayListExtra("key", lstSubItem);
            SubItemActivity.this.startActivity(myIntent);
        }else
        {
            Intent myIntent = new Intent(SubItemActivity.this, PrincipalActivitty.class);
            SubItemActivity.this.startActivity(myIntent);
        }
        finish();
    }

    private void InitFields(Intent intent){
        switch (action)
        {
            case INSERT:
                subItem = new SubItem();
                EnableFields(true);
                btnSubitemConfirm.setText(R.string.insert);
                break;
            case UPDATE:
                FillFields(intent);
                EnableFields(true);
                btnSubitemConfirm.setText(R.string.update);
                break;
            case DELETE:
                FillFields(intent);
                EnableFields(false);
                btnSubitemConfirm.setText(R.string.delete);
                break;
        }
    }

    private void EnableFields(boolean value){
        edtSubitemDescription.setEnabled(value);
        chkSubitemActive.setEnabled(value);
    }

    private void FillFields(Intent intent){
        subItem  = intent.getParcelableExtra("obj"); //if it's a string you stored.
        edtSubitemDescription.setText(subItem.Description);
        chkSubitemActive.setChecked(subItem.Active);
    }

    private void Insert(View v){
        Handler handler = new Handler();
        SubItem resultSubItem = SubItemBussiness.getInstance(getBaseContext()).Insert(subItem);
        if(resultSubItem != null && subItem.Description != null && subItem.Description.equals(resultSubItem.Description))
        {
            actionIsOk = true;
            Snackbar.make(v, R.string.subitem_insert_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(SubItemActivity.this, 2000); // 5000ms delay
        }
        else
        {
            actionIsOk = false;
            Snackbar.make(v, R.string.subitem_insert_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(SubItemActivity.this, 2000); // 5000ms delay
        }
    }

    private void Update(View v){
        Handler handler = new Handler();
        if(SubItemBussiness.getInstance(getBaseContext()).Update(subItem))
        {
            actionIsOk = true;
            Snackbar.make(v, R.string.subitem_update_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(SubItemActivity.this, 2000); // 5000ms delay
        }
        else
        {
            actionIsOk = false;
            Snackbar.make(v, R.string.subitem_update_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(SubItemActivity.this, 2000); // 5000ms delay
        }
    }

    private void Delete(View v){
        Handler handler = new Handler();
        if(SubItemBussiness.getInstance(getBaseContext()).Delete(subItem))
        {
            actionIsOk = true;
            Snackbar.make(v, R.string.subitem_delete_ok, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(SubItemActivity.this, 2000); // 5000ms delay
        }
        else
        {
            actionIsOk = false;
            Snackbar.make(v, R.string.subitem_delete_fail, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            handler.postDelayed(SubItemActivity.this, 2000); // 5000ms delay
        }
    }

    @Override
    public void run() {
        ArrayList<SubItem> lstProduct = SubItemBussiness.getInstance(getBaseContext()).Get(new SubItem());
        if(actionIsOk && !lstProduct.isEmpty())
        {
            Intent myIntent = new Intent(SubItemActivity.this, SubItemListActivity.class);
            myIntent.putParcelableArrayListExtra("key", lstProduct);
            SubItemActivity.this.startActivity(myIntent);
            finish();

        }else
        {
            Intent myIntent = new Intent(SubItemActivity.this, PrincipalActivitty.class);
            SubItemActivity.this.startActivity(myIntent);
            finish();
        }
    }
}

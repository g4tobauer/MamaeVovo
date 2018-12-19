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
import com.desenvolvigames.mamaevovo.entities.SubItem;

import java.util.ArrayList;

public class SubItemListActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{

    private FloatingActionButton fabSubItemAdd;
    private ListView ltvSubItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subitem_list);
        fabSubItemAdd = findViewById(R.id.fab_subitem_item_add);
        fabSubItemAdd.setOnClickListener(SubItemListActivity.this);

        ltvSubItemList = getListView();
        ltvSubItemList.setChoiceMode(ltvSubItemList.CHOICE_MODE_SINGLE);
        ltvSubItemList.setTextFilterEnabled(true);
        ltvSubItemList.setOnItemLongClickListener(SubItemListActivity.this);

        Intent myIntent = getIntent();
        ArrayList<SubItem> lstSubItem = myIntent.getParcelableArrayListExtra("key");
        setListAdapter(new ArrayAdapter<>(SubItemListActivity.this, android.R.layout.simple_list_item_1, lstSubItem));
    }

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(SubItemListActivity.this, SubItemActivity.class);
        myIntent.setAction(SubItemActivity.INSERT);
        SubItemListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        Intent myIntent = new Intent(SubItemListActivity.this, SubItemActivity.class);
        myIntent.setAction(SubItemActivity.UPDATE);
        myIntent.putExtra("key", (SubItem)parent.getItemAtPosition(position)); //Optional parameters
        SubItemListActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
        Intent myIntent = new Intent(SubItemListActivity.this, SubItemActivity.class);
        myIntent.setAction(SubItemActivity.DELETE);
        myIntent.putExtra("key", (SubItem)arg0.getItemAtPosition(pos)); //Optional parameters
        SubItemListActivity.this.startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SubItemListActivity.this, PrincipalActivitty.class);
        SubItemListActivity.this.startActivity(myIntent);
        finish();
    }
}

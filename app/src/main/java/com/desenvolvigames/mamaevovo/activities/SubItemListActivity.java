package com.desenvolvigames.mamaevovo.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private SubItem subItemOldTemp;
    private ArrayList<SubItem> lstSubItem;

    private static final int INSERT = 1;
    private static final int UPDATE = 2;

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
        lstSubItem = myIntent.getParcelableArrayListExtra("key");
        setListAdapter(new ArrayAdapter<>(SubItemListActivity.this, android.R.layout.simple_list_item_1, lstSubItem));
    }

    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        subItemOldTemp = (SubItem)parent.getItemAtPosition(position);
        Intent myIntent = new Intent(SubItemListActivity.this, SubItemActivity.class);
        myIntent.putExtra("key", subItemOldTemp);
        myIntent.setAction("UPDATE");
        SubItemListActivity.this.startActivityForResult(myIntent, UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            SubItem subItem;
            switch (requestCode)
            {
                case INSERT:
                    subItem = data.getParcelableExtra("result");
                    if(lstSubItem.size() == 0) subItem.Id = 0L;
                    else
                    {
                        Long id = lstSubItem.get(lstSubItem.size()-1).Id;
                        subItem.Id = id+1;
                    }
                    lstSubItem.add(subItem);
                    break;
                case UPDATE:
                    subItem = data.getParcelableExtra("result");
                    lstSubItem.set(lstSubItem.indexOf(subItemOldTemp), subItem);
                    subItemOldTemp = null;
                    break;
            }
            ((ArrayAdapter) ltvSubItemList.getAdapter()).notifyDataSetChanged();
        }else
        if (resultCode == Activity.RESULT_CANCELED)
        {
            //Write your code if there's no result
        }
    }//onActivityResult

    @Override
    public void onClick(View v){
        Intent myIntent = new Intent(SubItemListActivity.this, SubItemActivity.class);
        myIntent.setAction("INSERT");
        SubItemListActivity.this.startActivityForResult(myIntent, INSERT);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
        final SubItem subItem = (SubItem)arg0.getItemAtPosition(pos);

        CharSequence options[] = new CharSequence[]{"Sim", "Não"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseja deletar o subItem " + subItem.Description + " ?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    lstSubItem.remove(subItem);
                    ((ArrayAdapter)ltvSubItemList.getAdapter()).notifyDataSetChanged();
                }
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SubItemListActivity.this, PrincipalActivitty.class);
        SubItemListActivity.this.startActivity(myIntent);
        finish();
    }
}

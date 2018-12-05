package com.desenvolvigames.mamaevovo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.desenvolvigames.mamaevovo.R;

public class MenuActivitty extends AppCompatActivity implements View.OnClickListener{

    Button btnMenuCadastre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activitty);
        btnMenuCadastre = findViewById(R.id.bt_menu_cadastre);
        btnMenuCadastre.setOnClickListener(MenuActivitty.this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_menu_cadastre:
                Intent myIntent = new Intent(MenuActivitty.this, ProductActivity.class);
                myIntent.putExtra("key", ""); //Optional parameters
                MenuActivitty.this.startActivity(myIntent);
                break;
        }
    }
}

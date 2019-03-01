package com.desenvolvigames.mamaevovo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.desenvolvigames.mamaevovo.R;
import com.desenvolvigames.mamaevovo.bussiness.MovementDateBussiness;
import com.desenvolvigames.mamaevovo.entities.MovementDate;
import com.desenvolvigames.mamaevovo.helpers.DateHelper;
import com.desenvolvigames.mamaevovo.helpers.Filters.MovementDateFilter;

public class ReportsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtDias;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        edtDias = findViewById(R.id.edt_report_dias);
        btnCreate = findViewById(R.id.btn_report_create);
        btnCreate.setOnClickListener(ReportsActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_report_create:
                MovementDateBussiness.getInstance(getBaseContext()).Get(new MovementDateFilter(MovementDateFilter.INTERVALDATEBYDAYS, DateHelper.getCurrentDate(), -1));
                break;
        }
    }
}

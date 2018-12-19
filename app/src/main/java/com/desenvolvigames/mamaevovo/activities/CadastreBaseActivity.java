package com.desenvolvigames.mamaevovo.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class CadastreBaseActivity extends AppCompatActivity implements View.OnClickListener, Runnable
{
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    protected abstract void InitFields();
    protected abstract void FillFields();
    protected abstract void EnableFields(boolean value);
    protected abstract void Insert(View v);
    protected abstract void Update(View v);
    protected abstract void Delete(View v);
}

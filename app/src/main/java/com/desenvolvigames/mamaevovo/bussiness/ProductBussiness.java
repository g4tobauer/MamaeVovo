package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.ProductDataAccess;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.ArrayList;

public class ProductBussiness {
    private static ProductBussiness mInstance;
    private Context mContext;

    private ProductBussiness(){}

    public static ProductBussiness getInstance(Context context){
        if(mInstance==null)
            mInstance = new ProductBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<Product> Get(Product product){
        return ProductDataAccess.getInstance(mContext).Get(product);
    }

    public Product Insert(Product product){
        product.Price = Double.parseDouble(product.Price.toString().replace(",", "."));
        return ProductDataAccess.getInstance(mContext).Insert(product);
    }

    public boolean Update(Product product){
        product.Price = Double.parseDouble(product.Price.toString().replace(",", "."));
        return ProductDataAccess.getInstance(mContext).Update(product);
    }

    public boolean Delete(Product product){
        return ProductDataAccess.getInstance(mContext).Delete(product);
    }
}

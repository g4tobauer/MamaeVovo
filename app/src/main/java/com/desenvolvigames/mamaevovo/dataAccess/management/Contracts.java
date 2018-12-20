package com.desenvolvigames.mamaevovo.dataAccess.management;

import android.provider.BaseColumns;

public final class Contracts {
    private Contracts() {}

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "Product";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_UNIT = "Unit";
        public static final String COLUMN_NAME_USASUBITENS = "UsaSubItens";
        public static final String COLUMN_NAME_PRICE = "Price";
        public static final String COLUMN_NAME_OBS = "Obs";
    }

    public static class SubItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "SubItem";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_ACTIVE = "Active";
    }

    public static class SalesOrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "SalesOrder";
        public static final String COLUMN_NAME_IDSALESORDERITEM = "IdSalesOrderItem";
    }

    public static class SalesOrderItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "SalesOrderItem";
        public static final String COLUMN_NAME_IDPRODUCT = "IdProduct";
        public static final String COLUMN_NAME_AMOUNT = "Quantidade";
    }

    public static class SalesOrderSubItemByItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "SalesOrderSubItemByItem";
        public static final String COLUMN_NAME_IDSALESORDERITEM = "IdSalesOrderItem";
    }
}
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
    public static class MenuEntry implements BaseColumns {
        public static final String TABLE_NAME = "Menu";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_ACTIVE = "Active";
    }
}
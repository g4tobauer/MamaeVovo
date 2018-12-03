package com.desenvolvigames.mamaevovo.dataAccess.management;

import android.provider.BaseColumns;

public final class Contracts {
    private Contracts() {}
    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "Product";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
    }
}
package com.desenvolvigames.mamaevovo.helpers;

public enum ProductUnitEnum
{
    UN, KG;
    public static ProductUnitEnum fromInteger(int x) {
        switch(x) {
            case 0:
                return UN;
            case 1:
                return KG;
        }
        return null;
    }
    public static Integer toInteger(ProductUnitEnum x) {
        switch(x) {
            case UN:
                return 0;
            case KG:
                return 1;
        }
        return null;
    }
}

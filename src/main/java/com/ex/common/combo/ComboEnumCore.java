package com.ex.common.combo;

import java.util.ArrayList;
import java.util.List;

public class ComboEnumCore {

    interface EnumComboList {
        String getLabel();
        String getValue();
        String getValueLabel();
    }

    //---------------------------------------------------------
    public static List getEnumList(Class<?> aClass) {
        Class<EnumComboList> enumClass = (Class<EnumComboList>) aClass;
        List list = new ArrayList();
        for (EnumComboList enumItem : enumClass.getEnumConstants()) {
            list.add(new ComboDTO(enumItem.getValue(),enumItem.getLabel()));
        }
        return list;
    }

    //---------------------------------------------------------
    public static String getEnumLabel(Class<?> aClass, String value) {
        Class<EnumComboList> enumClass = (Class<EnumComboList>) aClass;
        for (EnumComboList enumItem : enumClass.getEnumConstants()) {
            if (enumItem.getValue().equals(value)) {
                return enumItem.getLabel();
            }
        }
        return null;
    }
}

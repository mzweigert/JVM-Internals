package com.jvm.internals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JSONParser {

    public String toJSON(Object o) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");
        Field[] declaredFields = o.getClass().getDeclaredFields();
        int size = declaredFields.length;

        for (int i = 0; i < size; i++) {
            declaredFields[i].setAccessible(true);
            builder.append("\"").append(declaredFields[i].getName()).append("\" : ");
            builder.append(checkTypeAndReturnValue(declaredFields[i].getType(), declaredFields[i].get(o)));
            if (i != (size - 1)) {
                builder.append(", ");
            }
        }
        builder.append(" }");
        return builder.toString();
    }

    private String checkTypeAndReturnValue(Class<?> type, Object val) throws IllegalAccessException {
        if(val == null){
            return null;
        }
        else if (checkNumericTypeField(type)) {
            return val.toString();
        } else if (checkQuotationMarkTypeField(type)) {
            return "\"" + val + "\"";
        } else if (Collection.class.isAssignableFrom(type)) {
            return buildJsonForCollection(val);
        } else {
            return toJSON(val);
        }
    }

    private boolean checkQuotationMarkTypeField(Class<?> clazz) {
        return Date.class.isAssignableFrom(clazz) ||
                String.class.isAssignableFrom(clazz) ||
                Enum.class.isAssignableFrom(clazz) ||
                Character.class.isAssignableFrom(clazz) ||
                char.class.isAssignableFrom(clazz);
    }

    private boolean checkNumericTypeField(Class<?> clazz) {
        return (Number.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                clazz.isPrimitive()) && !char.class.isAssignableFrom(clazz);
    }

    private String buildJsonForCollection(Object o) throws IllegalAccessException {
        List list = (ArrayList) o;
        if (list == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" [ ");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Object object = list.get(i);
            builder.append(checkTypeAndReturnValue(object.getClass(), object));
            if (i != (size - 1)) {
                builder.append(", ");
            }
        }
        builder.append(" ] ");
        return builder.toString();
    }

}

package com.jvm.internals;

import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

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
        } else if (type.isArray()){
            return buildJsonForCollection(arrayToList(val));
        } else if (Collection.class.isAssignableFrom(type) ) {
            return buildJsonForCollection((ArrayList)val);
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

    private String buildJsonForCollection(List list) throws IllegalAccessException {
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

    private List arrayToList(Object array){
        if(array == null){
            return Lists.newArrayList();
        }
        List list = Lists.newArrayList();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            list.add(Array.get(array, i));
        }
        return list;
    }
}

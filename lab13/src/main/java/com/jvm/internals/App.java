package com.jvm.internals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public void loadClassesAndMeasureTime() throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {

        long elapsedTime = System.currentTimeMillis();
        for(int i=0; i<5000; i++){
            Class c = Class.forName(String.format("com.jvm.internals.dummy.DummyClass%d", i));
            Object o = c.newInstance();
            Method multiply = c.getMethod("multiply", int.class);
            multiply.invoke(o , i);
        }
        System.out.println("-------------- Elapsed Time --------------" );
        System.out.println(System.currentTimeMillis() - elapsedTime);
        System.out.println("------------------------------------------" );

    }
    public static void main(String[] args) {
        try {
            new App().loadClassesAndMeasureTime();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

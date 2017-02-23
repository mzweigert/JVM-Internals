package com.jvm.internals;

public class OutOfMemoryErrorExample {

    private static Object[] newOutOfMemoryError(){
        Long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println(maxMemory);
        return new Object[(int) (maxMemory + 1)];
    }
    public static void main(String[] args) throws Exception {
       newOutOfMemoryError();
    }
}


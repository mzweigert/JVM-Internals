package com.jvm.internals;

/**
 * Created by Mat on 09.05.2017.
 */
public class ArrayListNative {

    private long pointer;
    private static final String LIB_NAME = "arrayListNative";
    static {
        System.loadLibrary(LIB_NAME);
    }

    public native int size();
    public native boolean isEmpty();
    public native boolean add(int element);
    public native int remove(int element);
    public native int get(int index);
    public native void clear();


    public long getPointer() {
        return pointer;
    }
}

package com.jvm.internals;

import com.sun.management.GarbageCollectorMXBean;
import sun.management.ManagementFactoryHelper;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Mat on 16.03.2017.
 */
public class GarbageCollectorBenchmark {

    private static final long MILION = 1000000L;
    private static final int _1024 = 1024;
    private static final int _4096 = 4096;
    private static final boolean useFixedObjectSize = true; // ZMIENIC JESLI ROZMIAR OBIIEKTÓW MA BY LOSOWY

    public static void main(String[] args) {
        long startTime, estimatedTime;
        Map<String, String> values = getVMParams();
        Random random = new Random();
        startTime = System.currentTimeMillis();
        for(int i = 0; i < MILION ; i++){
            new TestObject(useFixedObjectSize ? _4096 : _1024 + random.nextInt(_4096 - _1024 + 1));
        }
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Czas : " + estimatedTime + ", Algorytm GC : " + values.get("GCAlgorithm") + ", Rozmiar sterty : " + values.get("heapSize"));

    }

    private static  Map<String, String> getVMParams(){
        List<String> jvmArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
        Map<String, String> values = new HashMap<String, String>();
        for (String arg : jvmArgs){
            if(arg.contains("-Xms") || arg.contains("-Xmx")){
                values.put("heapSize", arg.substring(4));
            } else if(arg.contains("-XX:+Use")){
                values.put("GCAlgorithm", arg.substring(8, arg.length() - 2));
            }
        }
        return values;
    }
    private static class TestObject{
        int[] testArray;

        public TestObject(int size){
            this.testArray = new int[size];
        }
    }
}

package com.jvm.internals;

import static spark.Spark.*;
import static spark.Spark.stop;

/**
 * Created by Mat on 06.04.2017.
 */
public class RestApi {

    private static void threadSleep(int ms){
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    @MeasureTime
    private static String test1(){
        threadSleep(1000);
        return "test1";
    }

    @MeasureTime
    private static String test2(){
        threadSleep(2000);
        return "test2";
    }

    @MeasureTime
    private static String stopServ(){
        threadSleep(1000);
        stop();
        return "Server stopped";
    }

    @MeasureTime
    private static String smth(String param) {
        threadSleep(1234);
        return param;
    }

    public static void main(String[] args){
        get("/test1", (req, res) -> test1());
        get("/test2", (req, res) -> test2());
        get("/stop", (req, res) -> stopServ());
        get("/:param", (req, res) -> smth(req.params(":param")));
    }

}

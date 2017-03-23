package com.jvm.internals;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mat on 23.03.2017.
 */
public class SimpleDateFormatThreadSafe {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final int THREAD_SIZE = 100;
    private static String createRandomDate(){
        Random random = new Random();
        int year  = (random.nextInt(LocalDate.now().getYear() - 1970) + 1) + 1970;
        int month = (random.nextInt(12 - 1) +1) + 1;
        int day = (random.nextInt(28 - 1) + 1) + 1;
        return new StringBuilder()
                .append(year)
                .append("-")
                .append(month < 10 ? "0" + month : month)
                .append("-")
                .append(day < 10 ? "0" + day : day)
                .toString();
    }

    private static void parseToDate(String date, boolean sync){
        try {
            if(sync){
                synchronized (format){
                    Date formatDate = format.parse(date);
                    checkYearAndDisplay(formatDate, sync);
                }
            } else {
                Date formatDate = format.parse(date);
                checkYearAndDisplay(formatDate, sync);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkYearAndDisplay(Date formatDate, boolean sync) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate);
        int year = calendar.get(Calendar.YEAR);

        if(year > LocalDate.now().getYear() || year < 1970){
            System.out.println("Rok jest nieprawidlowy: " + year);
        } else {
            System.out.println("Synchronized? :" + sync + ", Date:" + formatDate);
        }
    }

    private static List<Callable<Void>> createCallables(int threadsSize, final boolean sync) {
        List<Callable<Void>> callables = Lists.newArrayList();
        for(int i = 0 ; i < threadsSize ; i++){
            callables.add(new Callable<Void>() {
                public Void call() throws Exception {
                    parseToDate(createRandomDate(), sync);
                    return null;
                }
            });
        }

        return callables;
    }

    private static void runThreads(int threadsSize, boolean sync){
        List<Callable<Void>> callables = createCallables(threadsSize, sync);
        ExecutorService executor = Executors.newFixedThreadPool(threadsSize);
        try {
            executor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        runThreads(THREAD_SIZE, false);
        runThreads(THREAD_SIZE, true);
    }
}

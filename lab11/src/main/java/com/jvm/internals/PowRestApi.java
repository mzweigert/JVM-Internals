package com.jvm.internals;

import com.jvm.internals.mbean.ServiceMonitor;
import com.jvm.internals.mbean.ServiceStatsMonitor;
import com.jvm.internals.service.SomeService;

import javax.management.*;
import java.lang.management.ManagementFactory;

import static spark.Spark.*;
import static spark.Spark.stop;

/**
 * Created by Mat on 06.04.2017.
 */
public class PowRestApi {

    private static SomeService someService = new SomeService();

    private static String stopServ(){
        stop();
        return "Server stopped";
    }
    private static Integer pow(String liczba) {
        return ((Double)Math.pow(Double.valueOf(liczba), someService.getExponent())).intValue();
    }

    public static void main(String[] args) throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName monitorName = new ObjectName("com.jvm.internals:type=Server,name=SomeService");
        ServiceMonitor sm = new ServiceMonitor(someService);
        mbs.registerMBean(sm, monitorName);

        ObjectName statsMonitorName = new ObjectName("com.jvm.internals:type=Server,name=ServiceStats");
        ServiceStatsMonitor ssm = new ServiceStatsMonitor(someService);
        mbs.registerMBean(ssm, statsMonitorName);

        get("/potega/:base", (req, res) -> pow(req.params(":base")));
        get("/stop", (req, res) -> stopServ());

    }



}

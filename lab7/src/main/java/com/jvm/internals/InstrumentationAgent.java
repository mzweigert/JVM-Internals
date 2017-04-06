package com.jvm.internals;

import java.lang.instrument.Instrumentation;

/**
 * Created by Mat on 06.04.2017.
 */
public class InstrumentationAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new JavassistTransformer());
    }

}

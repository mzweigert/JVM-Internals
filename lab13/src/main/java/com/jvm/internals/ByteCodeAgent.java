package com.jvm.internals;

import java.lang.instrument.Instrumentation;

public class ByteCodeAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        // Transformer registration
        inst.addTransformer(new JavassistTransformer());
        //inst.addTransformer(new ASMTransformer());
    }
}

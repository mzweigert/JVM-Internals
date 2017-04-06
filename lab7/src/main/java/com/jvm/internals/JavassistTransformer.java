package com.jvm.internals;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by Mat on 06.04.2017.
 */
public class JavassistTransformer implements ClassFileTransformer {

    private static final String ANNOTATION = "com.jvm.internals.MeasureTime";
    private static final String INSERT_BEFORE = " elapsedTime = System.currentTimeMillis(); ";
    private static final String INSERT_AFTER = " elapsedTime = System.currentTimeMillis() - elapsedTime; " ;
    private static final String VARIABLE_NAME = " elapsedTime ";
    private static final CtClass VARIABLE_TYPE =  CtClass.longType;


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] result = classfileBuffer;

        if(className == null || !className.contains("com/jvm/internals")){
            return result;
        }

        try {
            String dotClassName = className.replace('/', '.');
            ClassPool cp = ClassPool.getDefault();
            CtClass ctClazz = cp.get(dotClassName);

            Class measureAnnotation = Class.forName(ANNOTATION);
            for (CtMethod method : ctClazz.getDeclaredMethods()){
                if (method.getAnnotation(measureAnnotation) != null){
                    System.out.println("Transforming " + ctClazz.getName());
                    method.addLocalVariable("elapsedTime", CtClass.longType);
                    method.insertBefore(INSERT_BEFORE);
                    method.insertAfter(" { " + INSERT_AFTER + "System.out.println(\"" + method.getName() + " elapsedTime = \" + elapsedTime); }");
                }
            }
            result = ctClazz.toBytecode();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }


        return result;
    }
}

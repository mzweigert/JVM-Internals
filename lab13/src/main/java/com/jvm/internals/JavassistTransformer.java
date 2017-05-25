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

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] result = classfileBuffer;


        if(className == null || !className.contains("com/jvm/internals/dummy/DummyClass")){
            return result;
        }
        try {
            String dotClassName = className.replace('/', '.');
            ClassPool cp = ClassPool.getDefault();
            CtClass ctClazz = cp.get(dotClassName);
            for (CtMethod method : ctClazz.getDeclaredMethods()){
                method.insertBefore("System.out.println(\"Invoking : " + method.getName() + " from " + getShortClassName(dotClassName) + " \");");
            }
            result = ctClazz.toBytecode();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getShortClassName(String dotClassName){
        return dotClassName.substring(dotClassName.lastIndexOf(".") + 1);
    }
}

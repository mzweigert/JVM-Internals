package com.jvm.internals;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ASMTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] bytes) throws IllegalClassFormatException {

        byte[] result = bytes;

        if (className.contains("DummyClass")) {
            ClassWriter cw = new ClassWriter(0);
            ClassReader cr = new ClassReader(bytes);
            cr.accept(new PrintStringClassAdapter(cw, className), 0);
            result = cw.toByteArray();
        }

        return result;
    }

    private class PrintStringClassAdapter extends ClassVisitor {

        private String className;
        public PrintStringClassAdapter(ClassVisitor cv, String className) {
            super(Opcodes.ASM4, cv);
            this.className = className;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name,
                                         String descriptor, String signature, String[] exceptions) {
            return new PrintStringMethodAdapter(super.visitMethod(access, name,
                    descriptor, signature, exceptions), name, descriptor, className);
        }
    }

    private class PrintStringMethodAdapter extends MethodVisitor {
        private String className;
        private String methodName;
        private String methodDescriptor;

        public PrintStringMethodAdapter(MethodVisitor visitor, String name,
                                        String descriptor, String className) {
            super(Opcodes.ASM4, visitor);
            this.className = className;
            this.methodName = name;
            this.methodDescriptor = descriptor;
        }

        @Override
        public void visitCode() {
            if (methodName.equals("multiply")) {
                // trigger the super class
                super.visitCode();
                // load the system.out field into the stack
                super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System",
                        "out", "Ljava/io/PrintStream;");
                // load the constant string we want to print into the stack
                // this string is created by the values we get from ASM
                super.visitLdcInsn("ASM Invoking : " + methodName + "  from " + getShortClassName(className)
                        + methodDescriptor);
                // trigger the method instruction for 'println'
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/io/PrintStream", "println",
                        "(Ljava/lang/String;)V", false);
            }
        }
    }

    private String getShortClassName(String slashClassName){
        return slashClassName.substring(slashClassName.lastIndexOf("/") + 1);
    }

}

package com.jvm.internals;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParserExampleTest {

    public static void main(String[] args) throws Exception {
        Student sampleObject = createSampleStudent();
        long startTime, estimatedTime;
        startTime = System.nanoTime();
        new JSONParser().toJSON(sampleObject);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Czas wykonywania mojego algorytmu : " + estimatedTime);
        startTime = System.nanoTime();
        new Gson().toJson(sampleObject);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Czas wykonywania algorytmu GSon : " + estimatedTime);
        startTime = System.nanoTime();
        new c(sampleObject);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Czas wykonywania algorytmu Org Json : " + estimatedTime);

    }

    private static Student createSampleStudent() {
        return new Student("Mateusz", "Zweigert", 23, 175, Type.TYPA_A, createSampleColegues(), createSampleGrades(), 'g');
    }

    private static List<Student> createSampleColegues() {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("asd", "asd", 11, 11, Type.TYPE_B, null, createSampleGrades(), '1'));
        students.add(new Student("123", "gg", 22, 11, Type.TYPA_A, null, createSampleGrades(), 'a'));
        students.add(new Student("321", "ddd", 11, 22, Type.TYPA_A, null, createSampleGrades(), 'b'));
        return students;
    }

    private static int[] createSampleGrades() {
        int[] grades = new int[5];
        for(int i=0; i< 5; i++){
            grades[i] = i *2;
        }
        return grades;
    }

    static enum Type {
        TYPA_A, TYPE_B;
    }

    static class Student {
        String name;
        String surname;
        Integer age;
        Double height;
        Type type;
        List<Student> colegues;
        int[] grades;
        Character b;

        public Student(String name, String surname, int age, double height, Type type, List<Student> colegues, int[] grades, char b) {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.height = height;
            this.type = type;
            this.colegues = colegues;
            this.grades = grades;
            this.b = b;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", age=" + age +
                    ", height=" + height +
                    ", type=" + type +
                    ", colegues=" + colegues +
                    ", grades=" + grades +
                    ", b=" + b +
                    '}';
        }
    }

}


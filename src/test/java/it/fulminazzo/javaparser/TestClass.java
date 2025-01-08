package it.fulminazzo.javaparser;

import java.util.Objects;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class TestClass {
    public static int publicStaticField = 1;
    static int packageStaticField = 1;
    protected static int protectedStaticField = 1;
    private static int privateStaticField = 1;

    public Double publicField = 1.0;
    double packageField = 1.0;
    protected double protectedField = 1.0;
    private double privateField = 1.0;

    private final int i;
    private final Boolean b;

    public TestClass() {
        i = 0;
        b = null;
    }

    public TestClass(int i, Boolean b) {
        this.i = i;
        this.b = b;
    }

    TestClass(boolean b) {
        i = 0;
        this.b = b;
    }

    private TestClass(float f) {
        i = (int) f;
        b = null;
    }

    public static int publicStaticMethod() {
        return 1;
    }

    public static int publicStaticMethod(int n, Boolean b) {
        return b ? n : 1;
    }

    static int packageStaticMethod() {
        return 1;
    }

    protected static int protectedStaticMethod() {
        return 1;
    }

    private static int privateStaticMethod() {
        return 1;
    }


    public double publicMethod() {
        return 1.0;
    }

    public double publicMethod(double n, Boolean b) {
        return b ? n : 1.0;
    }

    public double publicMethod(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            if (s == null || s.isEmpty()) return i;
        }
        return strings.length;
    }

    double packageMethod() {
        return 1.0;
    }

    protected double protectedMethod() {
        return 1.0;
    }

    private double privateMethod() {
        return 1.0;
    }

    public void wave(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public Object returnNull() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TestClass && this.i == ((TestClass) o).i && Objects.equals(this.b, ((TestClass) o).b);
    }

}

package it.fulminazzo.javaparser.visitors.visitorobjects;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class TestClass {
    public static int publicStaticField = 1;
    static int packageStaticField = 1;
    protected static int protectedStaticField = 1;
    private static int privateStaticField = 1;

    public double publicField = 1.0;
    double packageField = 1.0;
    protected double protectedField = 1.0;
    private double privateField = 1.0;

    public TestClass() {}

    public TestClass(int i, Boolean b) {}

    TestClass(boolean b) {}

    private TestClass(float f) {}

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

    double packageMethod() {
        return 1.0;
    }

    protected double protectedMethod() {
        return 1.0;
    }

    private double privateMethod() {
        return 1.0;
    }

}

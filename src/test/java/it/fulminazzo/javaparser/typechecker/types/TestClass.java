package it.fulminazzo.javaparser.typechecker.types;

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

}

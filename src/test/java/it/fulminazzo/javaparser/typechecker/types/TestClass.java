package it.fulminazzo.javaparser.typechecker.types;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class TestClass {
    public static int publicStaticField = 1;
    static int packageStaticField = 1;
    protected static int protectedStaticField = 1;
    private static int privateStaticField = 1;

    public int publicField = 1;
    int packageField = 1;
    protected int protectedField = 1;
    private int privateField = 1;
}

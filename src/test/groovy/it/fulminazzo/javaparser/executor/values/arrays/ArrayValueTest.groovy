package it.fulminazzo.javaparser.executor.values.arrays

import spock.lang.Specification

class ArrayValueTest extends Specification {
    private static final byte b = 0
    private static final Byte bW = 0
    private static final short s = 0
    private static final Short sW = 0
    private static final char c = 0
    private static final Character cW = 0 as Character
    private static final int i = 0
    private static final Integer iW = 0
    private static final long l = 0L
    private static final Long lW = 0L
    private static final float f = 0.0f
    private static final Float fW = 0.0f
    private static final double d = 0.0d
    private static final Double dW = 0.0d
    private static final boolean bo = false
    private static final Boolean boW = false

    def 'test static array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(Integer, 3)

        and:
        def expected = new Integer[3]

        when:
        def actual = value.getValue()

        then:
        Arrays.equals(actual, expected)
    }

    def 'test dynamic array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(String, ['hello', 'world'])

        and:
        def expected = new String[]{'hello', 'world'}

        when:
        def actual = value.getValue()

        then:
        Arrays.equals(actual, expected)
    }

    def 'test ofPrimitive #componentsClass should return #expected'() {
        given:
        def size = 3

        when:
        def value = ArrayValue.ofPrimitive(componentsClass, size)
        def array = value.getValue()

        then:
        for (i in 0..size - 1) array[i] == expected[i]

        where:
        componentsClass | expected
        byte            | [b, b, b]
        Byte            | [bW, bW, bW]
        short           | [s, s, s]
        Short           | [sW, sW, sW]
        char            | [c, c, c]
        Character       | [cW, cW, cW]
        int             | [i, i, i]
        Integer         | [iW, iW, iW]
        long            | [l, l, l]
        Long            | [lW, lW, lW]
        float           | [f, f, f]
        Float           | [fW, fW, fW]
        double          | [d, d, d]
        Double          | [dW, dW, dW]
        boolean         | [bo, bo, bo]
        Boolean         | [boW, boW, boW]
    }

}
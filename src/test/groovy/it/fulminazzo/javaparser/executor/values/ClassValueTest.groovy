package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class ClassValueTest extends Specification {

    def 'test #value cast #classValue should return #expected'() {
        when:
        def cast = classValue.cast(value)

        then:
        cast == expected

        where:
        value                                         | classValue                  | expected
        PrimitiveValue.of((byte) 1)                   | ObjectClassValue.BYTE       | ObjectValue.of(Byte.valueOf((byte) 1))
        ObjectValue.of(Byte.valueOf((byte) 1))        | PrimitiveClassValue.BYTE    | PrimitiveValue.of((byte) 1)
        PrimitiveValue.of((short) 2)                  | ObjectClassValue.SHORT      | ObjectValue.of(Short.valueOf((short) 2))
        ObjectValue.of(Short.valueOf((short) 2))      | PrimitiveClassValue.SHORT   | PrimitiveValue.of((short) 2)
        PrimitiveValue.of((char) 'a')                 | ObjectClassValue.CHARACTER  | ObjectValue.of(Character.valueOf((char) 'a'))
        ObjectValue.of(Character.valueOf((char) 'a')) | PrimitiveClassValue.CHAR    | PrimitiveValue.of((char) 'a')
        PrimitiveValue.of(4)                          | ObjectClassValue.INTEGER    | ObjectValue.of(Integer.valueOf(4))
        ObjectValue.of(Integer.valueOf(4))            | PrimitiveClassValue.INT     | PrimitiveValue.of(4)
        PrimitiveValue.of(5L)                         | ObjectClassValue.LONG       | ObjectValue.of(Long.valueOf(5L))
        ObjectValue.of(Long.valueOf(5L))              | PrimitiveClassValue.LONG    | PrimitiveValue.of(5L)
        PrimitiveValue.of(6.0f)                       | ObjectClassValue.FLOAT      | ObjectValue.of(Float.valueOf(6.0f))
        ObjectValue.of(Float.valueOf(6.0f))           | PrimitiveClassValue.FLOAT   | PrimitiveValue.of(6.0f)
        PrimitiveValue.of(7.0d)                       | ObjectClassValue.DOUBLE     | ObjectValue.of(Double.valueOf(7.0d))
        ObjectValue.of(Double.valueOf(7.0d))          | PrimitiveClassValue.DOUBLE  | PrimitiveValue.of(7.0d)
        PrimitiveValue.of(true)                       | ObjectClassValue.BOOLEAN    | ObjectValue.of(true)
        ObjectValue.of(true)                          | PrimitiveClassValue.BOOLEAN | PrimitiveValue.of(true)
        PrimitiveValue.of((byte) 1)                   | PrimitiveClassValue.INT     | PrimitiveValue.of(1)
        PrimitiveValue.of((short) 2)                  | PrimitiveClassValue.INT     | PrimitiveValue.of(2)
        PrimitiveValue.of((char) 'a')                 | PrimitiveClassValue.INT     | PrimitiveValue.of(97)
        PrimitiveValue.of(97)                         | ObjectClassValue.BYTE       | ObjectValue.of((byte) 97)
        PrimitiveValue.of((char) 'a')                 | ObjectClassValue.BYTE       | ObjectValue.of((byte) 97)
        PrimitiveValue.of(97)                         | ObjectClassValue.SHORT      | ObjectValue.of((short) 97)
        PrimitiveValue.of((char) 'a')                 | ObjectClassValue.SHORT      | ObjectValue.of((short) 97)
        PrimitiveValue.of(97)                         | PrimitiveClassValue.CHAR    | PrimitiveValue.of((char) 'a')
        PrimitiveValue.of(5L)                         | PrimitiveClassValue.INT     | PrimitiveValue.of(5)
        PrimitiveValue.of(6.0f)                       | PrimitiveClassValue.INT     | PrimitiveValue.of(6)
        PrimitiveValue.of(7.0d)                       | PrimitiveClassValue.INT     | PrimitiveValue.of(7)
    }

    /**
     * NEW OBJECT
     */
    def 'test valid newObject (#parameters)'() {
        when:
        def actual = ClassValue.of(TestClass).newObject(parameters)

        then:
        actual == expected

        where:
        expected                               | parameters
        ObjectValue.of(new TestClass())        | new ParameterValues([])
        ObjectValue.of(new TestClass(1, true)) | new ParameterValues([PrimitiveValue.of(1), ObjectValue.of(true)])
    }

    def 'test method #toClass should always return a wrapper for java.lang.Class'() {
        given:
        def classValue = ClassValue.of(String).toClass()

        expect:
        classValue == ObjectClassValue.of(Class)
    }

    def 'test of #className should return #expected'() {
        given:
        def value = ClassValue.of(className)

        expect:
        value == expected

        where:
        className << [
                PrimitiveClassValue.values()*.name()*.toLowerCase(),
                ObjectClassValue.values()*.name().collect {
                    "${it[0]}${it.substring(1).toLowerCase()}"
                },
                Map.class.simpleName,
        ].flatten()
        expected << [
                PrimitiveClassValue.values(),
                ObjectClassValue.values(),
                new Refl<>("${ObjectClassValue.package.name}.CustomObjectClassValue",
                        Map).object,
        ].flatten()
    }

}
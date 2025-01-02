package it.fulminazzo.javaparser.executor.values


import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class PrimitiveClassValueTest extends Specification {

    def 'test #classValue should be compatible with #value'() {
        expect:
        classValue.compatibleWith(value)

        where:
        //TODO: wrapper objects
        classValue                  | value
        PrimitiveClassValue.BYTE | PrimitiveValue.of(1)
        PrimitiveClassValue.SHORT   | PrimitiveValue.of(1)
        PrimitiveClassValue.CHAR    | PrimitiveValue.of('c' as char)
        PrimitiveClassValue.CHAR    | PrimitiveValue.of(1)
        PrimitiveClassValue.INT     | PrimitiveValue.of(1)
        PrimitiveClassValue.INT     | PrimitiveValue.of('c' as char)
        PrimitiveClassValue.LONG    | PrimitiveValue.of(1)
        PrimitiveClassValue.LONG    | PrimitiveValue.of(1L)
        PrimitiveClassValue.LONG    | PrimitiveValue.of('c' as char)
        PrimitiveClassValue.FLOAT   | PrimitiveValue.of(1)
        PrimitiveClassValue.FLOAT   | PrimitiveValue.of(1L)
        PrimitiveClassValue.FLOAT   | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.FLOAT   | PrimitiveValue.of('c' as char)
        PrimitiveClassValue.DOUBLE  | PrimitiveValue.of(1)
        PrimitiveClassValue.DOUBLE  | PrimitiveValue.of(1L)
        PrimitiveClassValue.DOUBLE  | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.DOUBLE  | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.DOUBLE  | PrimitiveValue.of('c' as char)
        PrimitiveClassValue.BOOLEAN | PrimitiveValue.of(true)
    }

    def 'test #classValue should not be compatible with #value'() {
        expect:
        !classValue.compatibleWith(value)

        where:
        //TODO: wrapper objects
        classValue                   | value
        PrimitiveClassValue.BYTE     | PrimitiveValue.of('a')
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1L)
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(true)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of('a')
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1L)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(true)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of('a')
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1L)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(true)
        PrimitiveClassValue.INT      | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.INT      | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.INT      | PrimitiveValue.of(true)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(true)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(true)
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of(true)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of('a')
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1L)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1.0d)
    }

    def 'test toString of #value should return #expected'() {
        given:
        def string = value.toString()

        expect:
        string == expected

        where:
        value                       | expected
        PrimitiveClassValue.BYTE    | 'byte'
        PrimitiveClassValue.SHORT   | 'short'
        PrimitiveClassValue.CHAR    | 'char'
        PrimitiveClassValue.INT     | 'int'
        PrimitiveClassValue.LONG    | 'long'
        PrimitiveClassValue.FLOAT   | 'float'
        PrimitiveClassValue.DOUBLE  | 'double'
        PrimitiveClassValue.BOOLEAN | 'boolean'
    }

    def 'test values'() {
        expect:
        expected == value

        where:
        value << PrimitiveClassValue.values()
        expected << [
                PrimitiveClassValue.BYTE, PrimitiveClassValue.SHORT, PrimitiveClassValue.CHAR,
                PrimitiveClassValue.INT, PrimitiveClassValue.LONG, PrimitiveClassValue.FLOAT,
                PrimitiveClassValue.DOUBLE, PrimitiveClassValue.BOOLEAN
        ]
    }
    
    def 'test valueOf #name should return #expected'() {
        given:
        def actual = PrimitiveClassValue.valueOf(name)

        expect:
        actual == expected

        where:
        expected                    | name
        PrimitiveClassValue.BYTE    | 'BYTE'
        PrimitiveClassValue.SHORT   | 'SHORT'
        PrimitiveClassValue.CHAR    | 'CHAR'
        PrimitiveClassValue.INT     | 'INT'
        PrimitiveClassValue.LONG    | 'LONG'
        PrimitiveClassValue.FLOAT   | 'FLOAT'
        PrimitiveClassValue.DOUBLE  | 'DOUBLE'
        PrimitiveClassValue.BOOLEAN | 'BOOLEAN'
    }

    def 'test valueOf invalid should throw'() {
        when:
        PrimitiveClassValue.valueOf('none')

        then:
        thrown(IllegalArgumentException)
    }

}
package it.fulminazzo.javaparser.typechecker.types.arrays

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.ClassType
import it.fulminazzo.javaparser.typechecker.types.PrimitiveClassType
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class ArrayClassTypeTest extends Specification {
    static final VALUE_TYPES = [
            PrimitiveType.BYTE, PrimitiveType.SHORT, PrimitiveType.CHAR,
            PrimitiveType.INT, PrimitiveType.LONG, PrimitiveType.FLOAT,
            PrimitiveType.DOUBLE, PrimitiveType.BOOLEAN
    ]

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast << [
                PrimitiveClassType.values().collect { new ArrayClassType(it) },
                ObjectClassType.values().collect { new ArrayClassType(it) },
                new ArrayClassType(ObjectClassType.of('Map')),
                new ArrayClassType(ObjectClassType.of('Collection')),
                new ArrayClassType(ObjectClassType.of('List')),
        ].flatten()
        type << [
                VALUE_TYPES.collect { new ArrayType(it) },
                ObjectType.values().collect { new ArrayType(it) },
                new ArrayType(ObjectType.of('List')),
                new ArrayType(ObjectType.of('List')),
                new ArrayType(ObjectType.of('List')),
        ].flatten()
    }

    def 'test invalid cast of #cast to #type'() {
        when:
        cast.cast(type)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidCast(cast, type).message

        where:
        cast << [
                PrimitiveClassType.values(),
                ObjectClassType.values(),
        ].flatten().collect { new ArrayClassType(it) }
        type << [
                VALUE_TYPES.reverse(),
                ObjectType.values().reverse(),
        ].flatten().collect { new ArrayType(it) }
    }

    def 'test toString'() {
        given:
        def arrayClassType = new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveClassType.INT)))

        when:
        def string = arrayClassType.toString()

        then:
        string == 'int[][][].class'
    }

    def 'test conversion of types'() {
        given:
        def arrayType = new ArrayType(new ArrayType(new ArrayType(PrimitiveType.INT)))

        when:
        def arrayClassType = arrayType.toClass()
        def newArrayType = arrayClassType.toType()

        then:
        newArrayType == arrayType
        arrayClassType == new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveClassType.INT)))
    }

    def 'test toJavaClass'() {
        given:
        def classType = new ArrayClassType(ClassType.of(String.simpleName))

        when:
        def clazz = classType.toJavaClass()

        then:
        clazz == String[].class
    }

    def 'test toClass'() {
        given:
        // int[][][]
        def classType = new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveClassType.INT)))
        def type = new ArrayType(new ArrayType(new ArrayType(PrimitiveType.INT)))

        expect:
        type.toClass() == classType
    }

    def 'test compatibleWith'() {
        given:
        // int[][][]
        def classType = new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveClassType.INT)))
        def type = new ArrayType(new ArrayType(new ArrayType(PrimitiveType.INT)))

        expect:
        classType.compatibleWith(type)
    }

    def 'test not compatibleWith #type'() {
        given:
        def classType = new ArrayClassType(PrimitiveClassType.INT)

        expect:
        !classType.compatibleWith(type)

        where:
        type << [
                PrimitiveType.values(),
                new ArrayType(PrimitiveType.BOOLEAN),
        ].flatten()
    }

}

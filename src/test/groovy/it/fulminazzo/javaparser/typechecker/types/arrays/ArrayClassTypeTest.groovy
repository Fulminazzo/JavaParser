package it.fulminazzo.javaparser.typechecker.types.arrays

import it.fulminazzo.javaparser.typechecker.types.ClassType
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.ValueType
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class ArrayClassTypeTest extends Specification {

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast << [
                PrimitiveType.values().collect { new ArrayClassType(it) },
                ClassObjectType.values().collect { new ArrayClassType(it) },
                new ArrayClassType(ClassObjectType.of('Map')),
                new ArrayClassType(ClassObjectType.of('Collection')),
                new ArrayClassType(ClassObjectType.of('List'))
        ].flatten()
        type << [
                [
                        ValueType.BYTE, ValueType.SHORT, ValueType.CHAR,
                        ValueType.NUMBER, ValueType.LONG, ValueType.FLOAT,
                        ValueType.DOUBLE, ValueType.BOOLEAN
                ].collect { new ArrayType(it) },
                ObjectType.values().collect { new ArrayType(it) },
                new ArrayType(ObjectType.of('List')),
                new ArrayType(ObjectType.of('List')),
                new ArrayType(ObjectType.of('List'))
        ].flatten()
    }

    def 'test conversion of types'() {
        given:
        def arrayType = new ArrayType(new ArrayType(new ArrayType(ValueType.NUMBER)))

        when:
        def arrayClassType = arrayType.toClassType()
        def newArrayType = arrayClassType.toType()

        then:
        newArrayType == arrayType
        arrayClassType == new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveType.INT)))
    }

    def 'test toJavaClass'() {
        given:
        def classType = new ArrayClassType(ClassType.of(String.simpleName))

        when:
        def clazz = classType.toJavaClass()

        then:
        clazz == String[].class
    }

    def 'test toClassType'() {
        given:
        // int[][][]
        def classType = new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveType.INT)))
        def type = new ArrayType(new ArrayType(new ArrayType(ValueType.NUMBER)))

        expect:
        type.toClassType() == classType
    }

    def 'test compatibleWith'() {
        given:
        // int[][][]
        def classType = new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveType.INT)))
        def type = new ArrayType(new ArrayType(new ArrayType(ValueType.NUMBER)))

        expect:
        classType.compatibleWith(type)
    }

    def 'test not compatibleWith #type'() {
        given:
        def classType = new ArrayClassType(PrimitiveType.INT)

        expect:
        !classType.compatibleWith(type)

        where:
        type << [
                ValueType.values(),
                new ArrayType(ValueType.BOOLEAN)
        ].flatten()
    }

}

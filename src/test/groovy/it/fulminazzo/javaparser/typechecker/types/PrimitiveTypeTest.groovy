package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.typechecker.OperationUtils
import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import java.lang.reflect.Method

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*;

class PrimitiveTypeTest extends Specification {

    def 'test check invalid'() {
        when:
        DOUBLE.check(DOUBLE, types)

        then:
        def e = thrown(TypeCheckerException)
        e.getMessage() == TypeCheckerException.invalidType(STRING, DOUBLE).message

        where:
        types << [
                STRING,
                [STRING, BOOLEAN, FLOAT]
        ]
    }

    def 'test check empty'() {
        when:
        Method method = Type.getDeclaredMethod('checkType', Type[].class)
        method.invoke(DOUBLE)

        then:
        thrown(IllegalArgumentException)
    }

    def 'test BYTE compatible with #type'() {
        expect:
        PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.CHARACTER
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.BYTE,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE, ObjectType.SHORT
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE,
                BOOLEAN, STRING,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT, DOUBLE,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN, STRING,
                ObjectType.BOOLEAN, ObjectType.STRING
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN, ObjectType.BOOLEAN
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                STRING,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.STRING
        ]
    }

}

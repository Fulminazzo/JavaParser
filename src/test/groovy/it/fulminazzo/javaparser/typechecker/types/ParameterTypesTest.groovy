package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class ParameterTypesTest extends Specification {

    def 'test toJavaClassArray of #parameter should return #expected'() {
        given:
        def types = new ParameterTypes([parameter])

        when:
        def clazz = types.toJavaClassArray()

        then:
        clazz.length == 1
        clazz[0] == expected

        where:
        parameter << [
                PrimitiveType.values(),
                ObjectType.values(),
                ObjectType.of(getClass())
        ].flatten()
        expected << [
                [char, int, long, float, double, boolean, byte, short],
                [Byte, Short, Character, Integer, Long, Float, Double, Boolean, String, Object],
                getClass()
        ].flatten()
    }

    def 'test toClass should throw exception'() {
        given:
        def types = new ParameterTypes([])

        when:
        types.toClass()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(ParameterTypes).message
    }

}
package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeTest extends Specification {
    private Type type

    void setup() {
        type = ObjectType.of(TestClass)
    }

    def 'test valid getField'() {
        when:
        def tuple = this.type.getField(field)

        then:
        tuple.getKey() == expectedType
        tuple.getValue() == expected

        where:
        field               | expectedType         | expected
        'publicStaticField' | PrimitiveType.INT    | ValueType.NUMBER
        'publicField'       | PrimitiveType.DOUBLE | ValueType.DOUBLE
    }

}
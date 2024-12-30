package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeTest extends Specification {
    private Type type

    void setup() {
        type = ObjectType.of(TestClass)
    }

    def 'test valid getField #field'() {
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

    def 'test cannot access field #field from getField'() {
        when:
        this.type.getField(field)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessField(TestClass.getDeclaredField(field)).message

        where:
        field << [
                'packageStaticField','protectedStaticField','privateStaticField',
                'packageField','protectedField','privateField'
        ]
    }

    def 'test field not found'() {
        when:
        this.type.getField('invalid')

        then:
        def e = thrown(TypeException)
        e.message == TypeException.fieldNotFound('invalid').message
    }

}
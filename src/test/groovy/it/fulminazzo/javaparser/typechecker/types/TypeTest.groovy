package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeTest extends Specification {
    private Type type
    private ClassType classType

    void setup() {
        type = ObjectType.of(TestClass)
        classType = ClassObjectType.of(TestClass)
    }

    def 'test valid getField #field'() {
        when:
        def actual = this.type.getField(field)

        then:
        actual == expected

        where:
        field               | expected
        'publicStaticField' | PrimitiveType.INT
        'publicField'       | PrimitiveType.DOUBLE
    }

    def 'test cannot access field #field from getField'() {
        when:
        this.type.getField(field)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessField(this.type.toClassType(), TestClass.getDeclaredField(field)).message

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
        e.message == TypeException.fieldNotFound(this.type.toClassType(), 'invalid').message
    }

    def 'test class valid getField #field'() {
        when:
        def actual = this.classType.getField(field)

        then:
        actual == expected

        where:
        field               | expected
        'publicField'       | PrimitiveType.DOUBLE
    }

    def 'test class cannot access non-static field'() {
        given:
        def field = 'packageField'

        when:
        this.classType.getField(field)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessStaticField(this.classType, field).message
    }

    def 'test class cannot access field #field from getField'() {
        when:
        this.classType.getField(field)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessField(this.classType, TestClass.getDeclaredField(field)).message

        where:
        field << [
                'packageStaticField','protectedStaticField','privateStaticField',
                'packageField','protectedField','privateField'
        ]
    }

    def 'test class field not found'() {
        when:
        this.classType.getField('invalid')

        then:
        def e = thrown(TypeException)
        e.message == TypeException.fieldNotFound(this.classType, 'invalid').message
    }

}
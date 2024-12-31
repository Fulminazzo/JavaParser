package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeTest extends Specification {
    private static ParameterTypes NO_PARAMETERS = new ParameterTypes([])

    private Type type
    private ClassType classType

    void setup() {
        this.type = ObjectType.of(TestClass)
        this.classType = ClassObjectType.of(TestClass)
    }

    def 'test check class method'() {
        when:
        def t = this.type.check(ObjectType)

        then:
        t == ObjectType.of(TestClass)
    }

    def 'test invalid check class method'() {
        when:
        this.type.check(ClassObjectType)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(ClassObjectType, this.type).message
    }

    def 'test is #clazz should return true for #obj'() {
        expect:
        obj.is(clazz)

        where:
        obj << [
                ValueType.values(),
                ValueType.values(),
                PrimitiveType.values(),
                PrimitiveType.values(),
                PrimitiveType.values(),
                ObjectType.OBJECT,
                ObjectType.OBJECT,
                ClassObjectType.values(),
                ClassObjectType.values(),
                ClassObjectType.values(),
                ClassObjectType.of(TestClass),
                ClassObjectType.of(TestClass)
        ].flatten()
        clazz << [
                ValueType.values().collect { ValueType },
                ValueType.values().collect { Type },
                PrimitiveType.values().collect { PrimitiveType },
                PrimitiveType.values().collect { ClassType },
                PrimitiveType.values().collect { Type },
                ObjectType,
                Type,
                ClassObjectType.values().collect { ClassObjectType },
                ClassObjectType.values().collect { ClassType },
                ClassObjectType.values().collect { Type },
                ClassType,
                Type
        ].flatten()
    }

    /**
     * GET FIELD
     */

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
        'publicStaticField'       | PrimitiveType.INT
    }

    def 'test class cannot access non-static field'() {
        given:
        def field = 'publicField'

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

    /**
     * GET METHOD
     */

    def 'test valid getMethod #method'() {
        when:
        def actual = this.type.getMethod(method, parameters)

        then:
        actual == expected

        where:
        method               | expected             | parameters
        'publicStaticMethod' | PrimitiveType.INT    | this.NO_PARAMETERS
        'publicMethod'       | PrimitiveType.DOUBLE | this.NO_PARAMETERS
    }

    def 'test cannot access method #method from getMethod'() {
        when:
        this.type.getMethod(method, this.NO_PARAMETERS)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessMethod(this.type.toClassType(), TestClass.getDeclaredMethod(method)).message

        where:
        method << [
                'packageStaticMethod','protectedStaticMethod','privateStaticMethod',
                'packageMethod','protectedMethod','privateMethod'
        ]
    }

    def 'test method not found'() {
        when:
        this.type.getMethod('invalid', this.NO_PARAMETERS)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.methodNotFound(this.type.toClassType(), 'invalid', this.NO_PARAMETERS).message
    }

    def 'test class valid getMethod #method #parameters'() {
        when:
        def actual = this.classType.getMethod(method, parameters)

        then:
        actual == expected

        where:
        method                     | expected           | parameters
        'publicStaticMethod'       | PrimitiveType.INT  | this.NO_PARAMETERS
    }

    def 'test class cannot access non-static method'() {
        given:
        def method = 'publicMethod'

        when:
        this.classType.getMethod(method, this.NO_PARAMETERS)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessStaticMethod(this.classType, method, this.NO_PARAMETERS).message
    }

    def 'test class cannot access method #method from getMethod'() {
        when:
        this.classType.getMethod(method, this.NO_PARAMETERS)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessMethod(this.classType, TestClass.getDeclaredMethod(method)).message

        where:
        method << [
                'packageStaticMethod','protectedStaticMethod','privateStaticMethod',
                'packageMethod','protectedMethod','privateMethod'
        ]
    }

    def 'test class method not found'() {
        when:
        this.classType.getMethod('invalid', this.NO_PARAMETERS)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.methodNotFound(this.classType, 'invalid', this.NO_PARAMETERS).message
    }

}
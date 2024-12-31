package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

import java.lang.reflect.Method

class ClassTypeTest extends Specification {
    private ClassType classType

    void setup () {
        this.classType = ClassType.of(TestClass)
    }

    def 'test method #toClassType should always return a wrapper for java.lang.Class'() {
        given:
        def classType = new MockClassType().toClassType()

        expect:
        classType == ClassObjectType.of(Class)
    }

    def 'test of #className should return #expected'() {
        given:
        def type = ClassType.of(className)

        expect:
        type == expected

        where:
        className <<  [
                PrimitiveType.values().collect { it.name().toLowerCase() },
                ClassObjectType.values().collect { it.name() } .collect {
                     "${it[0]}${it.substring(1).toLowerCase()}"
                },
                Map.class.simpleName
        ].flatten()
        expected << [
                PrimitiveType.values(),
                ClassObjectType.values(),
                new Refl<>("${ClassObjectType.package.name}.CustomClassObjectType",
                        ObjectType.of('Map')).getObject()
        ].flatten()
    }

    def 'test compatibleWith type called from Object method'() {
        given:
        def type = new MockType()
        def classType = new MockClassType()

        when:
        Method method = ClassType.getDeclaredMethod('compatibleWith', Object.class)

        then:
        type.isAssignableFrom(classType)
        method.invoke(classType, type)
    }

    def 'test not compatibleWith #object'() {
        given:
        def classType = new MockClassType()

        when:
        Method method = ClassType.getDeclaredMethod('compatibleWith', Object.class)

        then:
        !method.invoke(classType, object)

        where:
        object << [
                new Type() {
                    @NotNull
                    @Override
                    ClassType toClassType() {
                        return null
                    }
                },
                new Object()
        ]
    }

    /**
     * NEW OBJECT
     */
    def 'test valid newObject (#parameters)'() {
        when:
        def actual = this.classType.newObject(parameters)

        then:
        actual == expected

        where:
        expected                 | parameters
        ObjectType.of(TestClass) | new ParameterTypes([])
        ObjectType.of(TestClass) | new ParameterTypes([PrimitiveType.INT, ClassObjectType.BOOLEAN])
    }

    def 'test newObject (#parameters) should throw types mismatch'() {
        when:
        this.classType.newObject(parameters)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.typesMismatch(this.classType,
                TestClass.getDeclaredConstructor(types), parameters).message

        where:
        types                        | parameters
        new Class[]{int, Boolean}    | new ParameterTypes([PrimitiveType.DOUBLE, ClassObjectType.BOOLEAN])
        new Class[]{int, Boolean}    | new ParameterTypes([PrimitiveType.INT, ClassObjectType.STRING])
        new Class[]{int, Boolean}    | new ParameterTypes([PrimitiveType.DOUBLE, ClassObjectType.STRING])
    }

    def 'test cannot access constructor (#type)'() {
        when:
        this.classType.newObject(new ParameterTypes([type]))

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessMethod(this.classType, TestClass.getDeclaredConstructor(clazz)).message

        where:
        type                  | clazz
        PrimitiveType.FLOAT   | float
        PrimitiveType.BOOLEAN | boolean
    }

    def 'test constructor not found'() {
        given:
        def mockParameters = new ParameterTypes([PrimitiveType.INT, PrimitiveType.DOUBLE, PrimitiveType.SHORT])
        when:
        this.classType.newObject(mockParameters)

        then:
        def e = thrown(TypeException)
        e.message == TypeException.methodNotFound(this.classType, '<init>', mockParameters).message
    }

    static class MockType implements Type {

        @NotNull
        @Override
        ClassType toClassType() {
            return null
        }

    }

    static class MockClassType implements ClassType {

        @NotNull
        @Override
        Type cast(@NotNull Type type) {
            return null
        }

        @NotNull
        @Override
        Class<?> toJavaClass() {
            return null
        }

        @Override
        boolean compatibleWith(@NotNull Type type) {
            return type instanceof MockType
        }

        @NotNull
        @Override
        Type toType() {
            return null
        }

    }

}

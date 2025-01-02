package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
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
        classType == ObjectClassType.of(Class)
    }

    def 'test of #className should return #expected'() {
        given:
        def type = ClassType.of(className)

        expect:
        type == expected

        where:
        className <<  [
                PrimitiveClassType.values().collect { it.name().toLowerCase() },
                ObjectClassType.values().collect { it.name() } .collect {
                     "${it[0]}${it.substring(1).toLowerCase()}"
                },
                Map.class.simpleName
        ].flatten()
        expected << [
                PrimitiveClassType.values(),
                ObjectClassType.values(),
                new Refl<>("${ObjectClassType.package.name}.CustomObjectClassType",
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
        ObjectType.of(TestClass) | new ParameterTypes([PrimitiveClassType.INT, ObjectClassType.BOOLEAN])
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
        new Class[]{int, Boolean}    | new ParameterTypes([PrimitiveClassType.DOUBLE, ObjectClassType.BOOLEAN])
        new Class[]{int, Boolean}    | new ParameterTypes([PrimitiveClassType.INT, ObjectClassType.STRING])
        new Class[]{int, Boolean}    | new ParameterTypes([PrimitiveClassType.DOUBLE, ObjectClassType.STRING])
    }

    def 'test cannot access constructor (#type)'() {
        when:
        this.classType.newObject(new ParameterTypes([type]))

        then:
        def e = thrown(TypeException)
        e.message == TypeException.cannotAccessMethod(this.classType, TestClass.getDeclaredConstructor(clazz)).message

        where:
        type                  | clazz
        PrimitiveClassType.FLOAT   | float
        PrimitiveClassType.BOOLEAN | boolean
    }

    def 'test constructor not found'() {
        given:
        def mockParameters = new ParameterTypes([PrimitiveClassType.INT, PrimitiveClassType.DOUBLE, PrimitiveClassType.SHORT])
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

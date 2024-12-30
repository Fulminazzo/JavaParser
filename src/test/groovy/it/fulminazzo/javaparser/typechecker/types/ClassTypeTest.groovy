package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

import java.lang.reflect.Method

class ClassTypeTest extends Specification {

    def 'test of #className should return #expected'() {
        given:
        def type = ClassType.of(className)

        expect:
        type == expected

        where:
        className <<  [
                ClassObjectType.values().collect { it.name() } .collect {
                     "${it[0]}${it.substring(1).toLowerCase()}"
                },
                Map.class.simpleName
        ].flatten()
        expected << [
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
                    ClassType getClassType() {
                        return null
                    }
                },
                new Object()
        ]
    }

    static class MockType implements Type {

        @NotNull
        @Override
        ClassType getClassType() {
            return null
        }

    }

    static class MockClassType implements ClassType {

        @Override
        Class<?> toJavaClass() {
            return null
        }

        @Override
        boolean compatibleWith(@NotNull Type type) {
            return type instanceof MockType
        }

    }
}

package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class ClassValueTest extends Specification {

    def 'test method #toClassValue should always return a wrapper for java.lang.Class'() {
        given:
        def classValue = new MockClassValue().toClassValue()

        expect:
        classValue == ObjectClassValue.of(Class)
    }

    def 'test of #className should return #expected'() {
        given:
        def value = ClassValue.of(className)

        expect:
        value == expected

        where:
        className <<  [
                PrimitiveClassValue.values().collect { it.name().toLowerCase() },
                ObjectClassValue.values().collect { it.name() } .collect {
                    "${it[0]}${it.substring(1).toLowerCase()}"
                },
                Map.class.simpleName
        ].flatten()
        expected << [
                PrimitiveClassValue.values(),
                ObjectClassValue.values(),
                new Refl<>("${ObjectClassValue.package.name}.CustomObjectClassValue",
                        Map).getObject()
        ].flatten()
    }

    static class MockClassValue implements ClassValue<Object> {

        @Override
        boolean compatibleWith(@NotNull Value<?> value) {
            return false
        }

        @Override
        Class<Object> getValue() {
            return null
        }

    }

}
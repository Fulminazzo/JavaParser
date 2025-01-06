package it.fulminazzo.javaparser.typechecker.types.variables

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.TypeException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeFieldContainerTest extends Specification {

    def 'test set of field with type #type should throw exception'() {
        given:
        def container = new TypeFieldContainer(type, ObjectClassType.STRING, 'unmodifiable', ObjectType.STRING)

        when:
        container.set(ObjectType.STRING)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeException.cannotModifyFinalField('unmodifiable').message

        where:
        type << [ObjectType.of(MockClass), ObjectClassType.of(MockClass)]
    }

    class MockClass {
        final String unmodifiable = 'I love Java!'
    }

}
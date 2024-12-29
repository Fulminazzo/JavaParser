package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.javaparser.parser.node.values.BooleanValueLiteral
import it.fulminazzo.javaparser.parser.node.values.NumberValueLiteral
import it.fulminazzo.javaparser.typechecker.types.Type
import it.fulminazzo.javaparser.typechecker.types.ValueType
import spock.lang.Specification

class TypeCheckerTest extends Specification {
    private TypeChecker typeChecker

    void setup() {
        this.typeChecker = new TypeChecker()
    }

    def 'test valid and'() {
        given:
        Type type = this.typeChecker.visitAnd(new BooleanValueLiteral('true'),
                new BooleanValueLiteral('true'))

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid and'() {
        when:
        this.typeChecker.visitAnd(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first                           | second
        new BooleanValueLiteral('true') | new NumberValueLiteral('1')
        new NumberValueLiteral('1')     | new BooleanValueLiteral('false')
        new NumberValueLiteral('1')     | new NumberValueLiteral('1')
    }

}

package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import it.fulminazzo.javaparser.typechecker.types.*
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeCheckerLiteralTest extends Specification {
    private TypeChecker checker

    void setup() {
        this.checker = new TypeChecker(getClass())
    }

    def 'test visit literal from code #code should return #expected'() {
        given:
        this.checker.environment.declare(ObjectClassType.INTEGER, 'var', PrimitiveType.NUMBER)

        when:
        def read = this.checker.visitLiteralImpl(code)

        then:
        read == expected

        where:
        code                                                        | expected
        'val'                                                       | new LiteralType('val')
        'int'                                                       | PrimitiveClassType.INT
        'String'                                                    | ObjectClassType.STRING
        'System'                                                    | ClassType.of(System)
        'System.class'                                              | ClassType.of(Class)
        'System.out'                                                | ObjectType.of(PrintStream.canonicalName)
        'var'                                                       | PrimitiveType.NUMBER
        'var.TYPE'                                                  | ObjectType.of('Class')
        "${FirstInnerClass.canonicalName}.second"                   | ObjectType.of(FirstInnerClass.SecondInnerClass.canonicalName)
        "${FirstInnerClass.canonicalName}.second.version"           | PrimitiveType.NUMBER
        "${FirstInnerClass.SecondInnerClass.canonicalName}"         | ClassType.of(FirstInnerClass.SecondInnerClass.canonicalName)
        "${FirstInnerClass.SecondInnerClass.canonicalName}.version" | PrimitiveType.NUMBER
    }

    def 'test visit literal invalid field'() {
        given:
        def typeException = TypeException.fieldNotFound(ClassType.of(System), 'non_existent')

        when:
        this.checker.visitLiteralImpl('System.non_existent')

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.of(typeException).message
    }

    def 'test visit literal invalid literal'() {
        given:
        def literal = 'invalid.class'

        when:
        this.checker.visitLiteralImpl(literal)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.cannotResolveSymbol(literal).message
    }

    def 'test getTypeFromLiteral #literal'() {
        given:
        this.checker.environment.declare(PrimitiveClassType.INT, 'i', PrimitiveType.NUMBER)

        when:
        def tuple = this.checker.getTypeFromLiteral(literal)

        then:
        tuple == expected

        where:
        literal   | expected
        'int'     | new Tuple<>(PrimitiveClassType.INT, PrimitiveClassType.INT)
        'i'       | new Tuple<>(PrimitiveClassType.INT, PrimitiveType.NUMBER)
        'invalid' | new Tuple<>()
    }

    static class FirstInnerClass {
        public static SecondInnerClass second = new SecondInnerClass()

        static class SecondInnerClass {
            public static int version = 2
        }

    }

}
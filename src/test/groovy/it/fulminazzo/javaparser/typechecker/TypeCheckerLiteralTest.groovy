package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import it.fulminazzo.javaparser.typechecker.types.ClassType
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.ValueType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeCheckerLiteralTest extends Specification {

    def 'test visit literal from code #code should return #expected'() {
        given:
        def checker = new TypeChecker()

        and:
        checker.environment.declare(ClassObjectType.INTEGER, 'var', ValueType.NUMBER)

        when:
        def read = checker.visitLiteralImpl(code)

        then:
        read == expected

        where:
        code                                                        | expected
        'int'                                                       | PrimitiveType.INT
        'String'                                                    | ClassObjectType.STRING
        'System'                                                    | ClassType.of('System')
        'System.out'                                                | ObjectType.of(PrintStream.canonicalName)
        'var'                                                       | ValueType.NUMBER
        'var.TYPE'                                                  | ObjectType.of('Class')
        'var.sizeTable'                                             | new ArrayType(ValueType.NUMBER)
        'var.value'                                                 | ValueType.NUMBER
        "${FirstInnerClass.canonicalName}.second"                   | ObjectType.of(FirstInnerClass.SecondInnerClass.canonicalName)
        "${FirstInnerClass.canonicalName}.second.version"           | ValueType.NUMBER
        "${FirstInnerClass.SecondInnerClass.canonicalName}"         | ClassType.of(FirstInnerClass.SecondInnerClass.canonicalName)
        "${FirstInnerClass.SecondInnerClass.canonicalName}.version" | ValueType.NUMBER
    }

    def 'test getTypeFromLiteral #literal'() {
        given:
        def checker = new TypeChecker()

        and:
        checker.environment.declare(PrimitiveType.INT, 'i', ValueType.NUMBER)

        when:
        def tuple = checker.getTypeFromLiteral(literal)

        then:
        tuple == expected

        where:
        literal   | expected
        'int'     | new Tuple<>(PrimitiveType.INT, PrimitiveType.INT)
        'i'       | new Tuple<>(PrimitiveType.INT, ValueType.NUMBER)
        'invalid' | new Tuple<>()
    }

    static class FirstInnerClass {
        static SecondInnerClass second = new SecondInnerClass()

        static class SecondInnerClass {
            static int version = 2
        }

    }

}
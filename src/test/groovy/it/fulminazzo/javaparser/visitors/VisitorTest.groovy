package it.fulminazzo.javaparser.visitors

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.fulmicollection.utils.ClassUtils
import it.fulminazzo.fulmicollection.utils.ReflectionUtils
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.MockNode
import it.fulminazzo.javaparser.parser.node.Node
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.statements.Statement
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

import java.lang.reflect.Modifier

class VisitorTest extends Specification {

    def 'test accept mock node'() {
        given:
        def node = new MockNode('mock', 1)
        def visitor = new MockVisitor()

        when:
        def converted = node.accept(visitor)

        then:
        converted == "${node.name}${node.version}"
    }

    def "visitor should have method: visit#clazz.simpleName(#parameters.type.simpleName) "() {
        given:
        def methodName = "visit${clazz.simpleName}"

        when:
        def method = Visitor.class.getDeclaredMethods()
            .findAll { it.name == methodName }
            .findAll { it.parameterCount == parameters.length }
            .find { parameters.collect { f -> f.type } == it.parameterTypes.toList() }

        then:
        if (method == null) writeMethod(methodName, parameters)
        method != null

        where:
        clazz << nodeClasses()
        parameters << nodeClasses()
                .collect { new Refl<>(it) }
                .collect { it.nonStaticFields }
                .collect{ it.toArray() }
    }

    static writeMethod(def methodName, Object[] fieldParameters) {
        def cwd = System.getProperty('user.dir')
        def path = "${VisitorTest.class.package.name.replace('.', File.separator)}"
        def file = new File(cwd, "src/main/java/${path}${File.separator}${Visitor.class.simpleName}.java")

        def lines = file.readLines()
        def toWrite = lines.subList(0, lines.size() - 2)
        def stringParameters = fieldParameters.collect {
            def value = "${it.type.simpleName} ${it.name}"
            if (!ReflectionUtils.isPrimitive(it.type)) value = '@NotNull ' + value
            return value
        }.join(', ')
        toWrite.add("    T ${methodName}(${stringParameters});\n")
        toWrite.add('\n}')

        file.delete()
        toWrite.each { file << "${it}\n" }

        println "Updated ${Visitor.class.simpleName} class with method ${methodName}"
    }

    static nodeClasses() {
        ClassUtils.findClassesInPackage(Node.class.package.name)
                .findAll { !Modifier.isAbstract(it.modifiers) }
                .findAll { !it.isInterface() }
                .findAll { !it.simpleName.contains('Test') }
                .findAll { !it.simpleName.contains('Exception') }
                .findAll { !it.simpleName.contains('Mock') }
    }

    class MockVisitor implements Visitor<String> {

        String visitMockNode(String name, int version) {
            return "${name}${version}"
        }

        /**
         * UNUSED METHODS
         */

        @Override
        String visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value) {
            return null
        }

        @Override
        String visitMethodCall(@NotNull Node executor, @NotNull MethodInvocation invocation) {
            return null
        }

        @Override
        String visitMethodInvocation(@NotNull List<Node> parameters) {
            return null
        }

        @Override
        String visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
            return null
        }

        @Override
        String visitStaticArray(int size, @NotNull Node type) {
            return null
        }

        @Override
        String visitCodeBlock(@NotNull LinkedList<Statement> statements) {
            return null
        }

        @Override
        String visitJavaProgram(@NotNull LinkedList<Statement> statements) {
            return null
        }

        @Override
        String visitArrayLiteral(@NotNull Node type) {
            return null
        }

        @Override
        String visitEmptyLiteral() {
            return null
        }

        @Override
        String visitLiteralImpl(@NotNull String value) {
            return null
        }

        @Override
        String visitAdd(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitAnd(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitBitAnd(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitBitOr(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitBitXor(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitCast(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitDivide(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitGreaterThan(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitGreaterThanEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitLShift(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitLessThan(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitLessThanEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitModulo(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitMultiply(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitNewObject(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitNotEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitOr(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitRShift(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitReAssign(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitSubtract(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitURShift(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        String visitDecrement(boolean before, @NotNull Node operand) {
            return null
        }

        @Override
        String visitIncrement(boolean before, @NotNull Node operand) {
            return null
        }

        @Override
        String visitMinus(@NotNull Node operand) {
            return null
        }

        @Override
        String visitNot(@NotNull Node operand) {
            return null
        }

        @Override
        String visitBreak(@NotNull Node expr) {
            return null
        }

        @Override
        String visitContinue(@NotNull Node expr) {
            return null
        }

        @Override
        String visitDoStatement(@NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        String visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        String visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        String visitIfStatement(@NotNull CodeBlock code, @NotNull Node thenBranch, @NotNull Node expr) {
            return null
        }

        @Override
        String visitReturn(@NotNull Node expr) {
            return null
        }

        @Override
        String visitStatement(@NotNull Node expr) {
            return null
        }

        @Override
        String visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        String visitNullLiteral() {
            return null
        }

        @Override
        String visitBooleanValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        String visitCharValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        String visitDoubleValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        String visitFloatValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        String visitLongValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        String visitNumberValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        String visitStringValueLiteral(@NotNull String rawValue) {
            return null
        }
    }

}
package it.fulminazzo.javaparser.visitors

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.fulmicollection.utils.ClassUtils
import it.fulminazzo.fulmicollection.utils.ReflectionUtils
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.MockNode
import it.fulminazzo.javaparser.parser.node.Node
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.container.JavaProgram
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
        toWrite.add("    @NotNull T ${methodName}(${stringParameters});\n")
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
        @NotNull Optional<String> visitProgram(@NotNull JavaProgram program) {
            return null
        }

        @Override
        @NotNull String visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value) {
            return null
        }

        @NotNull
        @Override
        String visitMethodCall(@NotNull Node executor, @NotNull String methodName, @NotNull MethodInvocation invocation) {
            return null
        }

        @NotNull
        @Override
        String visitField(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitMethodInvocation(@NotNull List<Node> parameters) {
            return null
        }

        @Override
        @NotNull String visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
            return null
        }

        @Override
        @NotNull String visitStaticArray(int size, @NotNull Node type) {
            return null
        }

        @Override
        @NotNull String visitCodeBlock(@NotNull LinkedList<Statement> statements) {
            return null
        }

        @Override
        @NotNull String visitJavaProgram(@NotNull LinkedList<Statement> statements) {
            return null
        }

        @Override
        @NotNull String visitArrayLiteral(@NotNull Node type) {
            return null
        }

        @Override
        @NotNull String visitEmptyLiteral() {
            return null
        }

        @Override
        @NotNull String visitLiteralImpl(@NotNull String value) {
            return null
        }

        @Override
        @NotNull String visitAdd(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitAnd(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitBitAnd(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitBitOr(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitBitXor(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitCast(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitDivide(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitGreaterThan(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitGreaterThanEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitLShift(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitLessThan(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitLessThanEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitModulo(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitMultiply(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitNewObject(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitNotEqual(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitOr(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitRShift(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitReAssign(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitSubtract(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitURShift(@NotNull Node left, @NotNull Node right) {
            return null
        }

        @Override
        @NotNull String visitDecrement(boolean before, @NotNull Node operand) {
            return null
        }

        @Override
        @NotNull String visitIncrement(boolean before, @NotNull Node operand) {
            return null
        }

        @Override
        @NotNull String visitMinus(@NotNull Node operand) {
            return null
        }

        @Override
        @NotNull String visitNot(@NotNull Node operand) {
            return null
        }

        @Override
        @NotNull String visitBreak(@NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitContinue(@NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitDoStatement(@NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitReturn(@NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitStatement(@NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expr) {
            return null
        }

        @Override
        @NotNull String visitNullLiteral() {
            return null
        }

        @Override
        @NotNull String visitBooleanValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        @NotNull String visitCharValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        @NotNull String visitDoubleValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        @NotNull String visitFloatValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        @NotNull String visitLongValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        @NotNull String visitNumberValueLiteral(@NotNull String rawValue) {
            return null
        }

        @Override
        @NotNull String visitStringValueLiteral(@NotNull String rawValue) {
            return null
        }
    }

}
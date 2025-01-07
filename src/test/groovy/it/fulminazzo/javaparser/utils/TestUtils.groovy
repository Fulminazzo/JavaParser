package it.fulminazzo.javaparser.utils

import it.fulminazzo.fulmicollection.utils.StringUtils
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.util.function.Function

class TestUtils {

    /**
     * Converts the name of an {@link Enum} object to a method name compatible format.
     *
     * @param enumObject the enum object
     * @return the method name
     */
    static <E extends Enum<E>> String convertEnumName(@NotNull E enumObject) {
        "${StringUtils.capitalize(enumObject.name())}".replace('_', '')
    }

    /**
     * Generates a method associated with the given enum object.
     * Useful in some tests.
     *
     * @param targetClass       the class where to write the method
     * @param enumObject        the target enum object
     * @param commentGenerator  the comment to prepend to the method (optional)
     * @param modifiers         modifiers to prepend to the method (optional)
     * @param returnType        the return type of the method
     * @param nameGenerator     a generator function to obtain the method name
     * @param exception         an exception thrown by the method (optional)
     * @param bodyGenerator     the body of the method (optional)
     */
    static <E extends Enum<E>> void generateMethod(@NotNull Class targetClass, @NotNull E enumObject,
                                                   @Nullable Function<E, List<String>> commentGenerator,
                                                   @Nullable String modifiers,
                                                   @NotNull String returnType,
                                                   @NotNull Function<String, String> nameGenerator,
                                                   @Nullable Class<? extends Exception> exception,
                                                   @Nullable Function<E, List<String>> bodyGenerator) {
        def cwd = System.getProperty('user.dir')
        def classPath = "${targetClass.canonicalName.replace('.', File.separator)}"
        def file = new File(cwd, "src/main/java/${classPath}.java")
        def methodName = nameGenerator.apply(convertEnumName(enumObject))

        def lines = file.readLines()
        def toWrite = lines.subList(0, lines.size() - 2)
        toWrite.add('')
        // Comment
        if (commentGenerator != null) {
            toWrite.add('    /**')
            toWrite.add(commentGenerator.apply(enumObject).collect { '     * ' + it }.join('\n'))
            toWrite.add('     */')
        }
        def methodDeclaration = "${returnType} ${methodName}()"
        // Modifiers
        if (modifiers != null) methodDeclaration = "${modifiers} ${methodDeclaration}"
        methodDeclaration = "    ${methodDeclaration}"
        // Exception
        if (exception != null) methodDeclaration += " throws ${exception.simpleName}"
        // Body
        if (bodyGenerator == null) methodDeclaration += ";"
        else {
            def body = bodyGenerator.apply(enumObject)
                    .collect { '        ' + it }
                    .join('\n')
            methodDeclaration += " {\n${body}\n    }"
        }

        toWrite.add(methodDeclaration + '\n')
        toWrite.add('}')

        file.delete()
        toWrite.each { file << "${it}\n" }

        println "Updated ${targetClass.simpleName} class with method ${methodName}"
    }

}

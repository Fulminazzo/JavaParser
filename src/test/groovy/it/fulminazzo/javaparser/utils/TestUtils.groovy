package it.fulminazzo.javaparser.utils

import it.fulminazzo.fulmicollection.utils.StringUtils
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.util.function.Function

class TestUtils {

    /**
     * Generates a method associated with the given enum object.
     * Useful in some tests.
     *
     * @param targetClass   the class where to write the method
     * @param enumObject    the target enum object
     * @param comment       a comment to prepend to the method (optional)
     * @param modifiers     modifiers to prepend to the method (optional)
     * @param returnType    the return type of the method
     * @param nameGenerator a generator function to obtain the method name
     * @param exception     an exception thrown by the method (optional)
     * @param bodyGenerator the body of the method (optional)
     */
    static <E extends Enum<E>> void generateMethod(@NotNull Class targetClass, @NotNull E enumObject,
                                                   @Nullable String comment, @Nullable String modifiers,
                                                   @NotNull String returnType, @NotNull Function<String, String> nameGenerator,
                                                   @Nullable String exception,
                                                   @Nullable Function<E, String> bodyGenerator) {
        def cwd = System.getProperty('user.dir')
        def classPath = "${targetClass.canonicalName.replace('.', File.separator)}"
        def file = new File(cwd, "src/main/java/${classPath}.java")
        def enumName = StringUtils.capitalize(enumObject.name()).replace('_', '')
        def methodName = nameGenerator.apply(enumName)

        def lines = file.readLines()
        def toWrite = lines.subList(0, lines.size() - 2)
        // Comment
        if (comment != null) {
            toWrite.add('\n    /**')
            toWrite.add(comment)
            toWrite.add('     */')
        }
        def methodDeclaration = "${returnType} ${methodName}()"
        // Modifiers
        if (modifiers != null) methodDeclaration = "${modifiers} ${methodDeclaration}"
        // Exception
        if (exception != null) methodDeclaration += " throws ${exception}"
        // Body
        if (bodyGenerator == null) methodDeclaration += ";"
        else methodDeclaration += " {\n${bodyGenerator.apply(enumObject)}\n    }\n"

        toWrite.add(methodDeclaration)
        toWrite.add('}')

        file.delete()
        toWrite.each { file << "${it}\n" }

        println "Updated ${targetClass.simpleName} class with method ${methodName}"
    }

}

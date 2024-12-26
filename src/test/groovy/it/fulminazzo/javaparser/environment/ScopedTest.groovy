package it.fulminazzo.javaparser.environment

import it.fulminazzo.fulmicollection.utils.StringUtils
import spock.lang.Specification

class ScopedTest extends Specification {

    def 'verify Scoped has check method for ScopeType #scopeType'() {
        given:
        def methodName = generateMethodName(scopeType)

        when:
        def method = Scoped.class.getDeclaredMethods()
                .findAll { it.name == methodName }
                .find { it.parameterCount == 0 }

        then:
        if (method == null) writeCheckMethod(scopeType)
        method != null

        where:
        scopeType << ScopeType
    }

    def generateMethodName(ScopeType scopeType) {
        "check${StringUtils.capitalize(scopeType.name())}".replace('_', '')
    }

    def writeCheckMethod(ScopeType scopeType) {
        def cwd = System.getProperty('user.dir')
        def path = "${ScopedTest.class.package.name.replace('.', File.separator)}"
        def file = new File(cwd, "src/main/java/${path}${File.separator}${Scoped.class.simpleName}.java")
        def methodName = generateMethodName(scopeType)

        def lines = file.readLines()
        def toWrite = lines.subList(0, lines.size() - 2)
        toWrite.add('\n    /**')
        toWrite.add("     * Checks that the scope type is {@link ${ScopeType.simpleName}#${scopeType}}.")
        toWrite.add('     *')
        toWrite.add('     * @return this object')
        toWrite.add("     * @throws ${ScopeException.simpleName} thrown if the current scope type does not match")
        toWrite.add('     */')
        toWrite.add("    default ${Scoped.simpleName}<T> ${methodName}() throws ${ScopeException.simpleName} {")
        toWrite.add("        return checkScopeType(${ScopeType.simpleName}.${scopeType});")
        toWrite.add('    }\n')
        toWrite.add('}')

        file.delete()
        toWrite.each { file << "${it}\n" }

        println "Updated ${Scoped.class.simpleName} class with method ${methodName}"
    }

}
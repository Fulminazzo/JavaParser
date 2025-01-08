package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.TestClass
import spock.lang.Specification

class TypeExceptionTest extends Specification {

    def 'test get visibility modifier'() {
        given:
        def field = TestClass.getDeclaredField(fieldName)

        when:
        def visibility = TypeException.getVisibilityModifier(field)

        then:
        visibility == expected

        where:
        fieldName              | expected
        'publicStaticField'    | 'public'
        'packageStaticField'   | 'package'
        'protectedStaticField' | 'protected'
        'privateStaticField'   | 'private'
        'publicField'          | 'public'
        'packageField'         | 'package'
        'protectedField'       | 'protected'
        'privateField'         | 'private'
    }

}

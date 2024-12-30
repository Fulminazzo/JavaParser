package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeTest extends Specification {
    private Type type

    void setup() {
        type = ObjectType.of(TestClass)
    }

}
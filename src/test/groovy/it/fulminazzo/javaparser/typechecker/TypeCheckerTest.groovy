package it.fulminazzo.javaparser.typechecker

import spock.lang.Specification

class TypeCheckerTest extends Specification {
    private TypeChecker typeChecker

    void setup() {
        this.typeChecker = new TypeChecker()
    }

}

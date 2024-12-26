package it.fulminazzo.javaparser.environment

import spock.lang.Specification

class EnvironmentTest extends Specification {
    private Environment<Integer> environment

    void setup() {
        this.environment = new Environment().enterScope(ScopeType.CODE_BLOCK)
    }

    void cleanup() {
        this.environment.exitScope()
    }

}
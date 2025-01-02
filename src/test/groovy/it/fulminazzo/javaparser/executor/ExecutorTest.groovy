package it.fulminazzo.javaparser.executor


import spock.lang.Specification

class ExecutorTest extends Specification {
    private Executor executor

    void setup() {
        this.executor = new Executor(getClass())
    }

}
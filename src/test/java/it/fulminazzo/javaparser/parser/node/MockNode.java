package it.fulminazzo.javaparser.parser.node;

public class MockNode extends Node {
    final static String IGNORE = "Should be ignored";
    final String name;
    final int version;

    public MockNode(final String name, final int version) {
        this.name = name;
        this.version = version;
    }

}

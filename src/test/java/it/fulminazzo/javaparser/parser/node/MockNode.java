package it.fulminazzo.javaparser.parser.node;

class MockNode extends Node {
    final String name;
    final int version;

    public MockNode(final String name, final int version) {
        this.name = name;
        this.version = version;
    }

}

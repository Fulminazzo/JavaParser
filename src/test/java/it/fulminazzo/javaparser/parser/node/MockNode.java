package it.fulminazzo.javaparser.parser.node;

import lombok.Getter;

@Getter
public class MockNode extends NodeImpl {
    final static String IGNORE = "Should be ignored";
    final String name;
    final int version;

    public MockNode(final String name, final int version) {
        this.name = name;
        this.version = version;
    }

}

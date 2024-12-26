package it.fulminazzo.javaparser.parser.node

import spock.lang.Specification


class NodeTest extends Specification {

    def 'test hashCode'() {
        given:
        def node = new MockNode('MockNode', 1)

        expect:
        node.hashCode() == 'MockNode'.hashCode() + 1.hashCode()
    }

    def 'test equals'() {
        given:
        def node = new MockNode('MockNode', 1)
        def other = new MockNode('MockNode', 1)

        expect:
        node == other
    }

    def 'test not equals'() {
        given:
        def node = new MockNode('MockNode', 1)
        def other = new MockNode('MockNode', 2)

        expect:
        node != other
    }

    def 'test toString'() {
        given:
        def node = new MockNode('MockNode', 1)

        expect:
        node.toString() == 'MockNode(MockNode, 1)'
    }

}
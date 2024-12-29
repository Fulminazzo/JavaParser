package it.fulminazzo.javaparser.wrappers


import spock.lang.Specification

class ObjectWrapperTest extends Specification {
    private String string
    private ObjectWrapper wrapper

    void setup() {
        this.string = 'Hello, world!'
        this.wrapper = new MockWrapper(string)
    }

    def 'test hashCode'() {
        expect:
        this.wrapper.hashCode() == MockWrapper.hashCode() + this.string.hashCode()
    }

    def 'test equality'() {
        expect:
        this.wrapper == new MockWrapper(this.string)
    }

    def 'test inequality with #object'() {
        expect:
        !this.wrapper.equals(object)

        where:
        object << [
                null,
                new ObjectWrapper<>(10),
                new MockWrapper('Hello, enemy!')
        ]
    }

    def 'test toString'() {
        expect:
        this.wrapper.toString() == "${MockWrapper.class.simpleName}(${this.string})"
    }

    static class MockWrapper extends ObjectWrapper<String> {

        MockWrapper(String object) {
            super(object)
        }

    }

}

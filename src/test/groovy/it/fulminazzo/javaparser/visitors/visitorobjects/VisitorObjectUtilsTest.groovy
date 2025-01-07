package it.fulminazzo.javaparser.visitors.visitorobjects


import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.ParameterElements
import spock.lang.Specification

class VisitorObjectUtilsTest extends Specification {

    def 'test verifyExecutable of #parameters, #executable should return #expected'() {
        when:
        def actual = VisitorObjectUtils.verifyExecutable(new ParameterElements(parameters), executable)

        then:
        actual == expected

        where:
        parameters                                    | executable                                           | expected
        []                                            | TestClass.getMethod('publicMethod')                  | true
        [Element.of(1)]                               | TestClass.getMethod('publicMethod')                  | false
        [Element.of(1)]                               | TestClass.getMethod('publicMethod', double, Boolean) | false
        [Element.of(1), Element.of(2), Element.of(3)] | TestClass.getMethod('publicMethod', Boolean[])       | true
        [Element.of(1), Element.of(2), Element.of(3)] | TestClass.getMethod('publicMethod', double, Boolean) | false
    }

}

package it.fulminazzo.mojito.executor.values.arrays

import it.fulminazzo.mojito.executor.values.PrimitiveClassValue
import it.fulminazzo.mojito.executor.values.Value
import it.fulminazzo.mojito.executor.values.ValueException
import it.fulminazzo.mojito.executor.values.objects.ObjectClassValue
import spock.lang.Specification

class ArrayValueTest extends Specification {
    private ArrayValue<?> array

    void setup() {
        this.array = new ArrayValue<>(PrimitiveClassValue.DOUBLE, 3)
    }

    def 'test getField of "length" should return actual size'() {
        when:
        def field = this.array.getField('length')

        then:
        field.variable.value == 3
    }

    def 'test getField should have normal behaviour in any other case'() {
        when:
        this.array.getField('not_existing')

        then:
        def e = thrown(ValueException)
        e.message == ValueException.fieldNotFound(this.array.toClass(), 'not_existing').message
    }

    def 'test static array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(PrimitiveClassValue.INT, 3)

        and:
        def expected = new int[]{0, 0, 0}

        when:
        def actual = value.value

        then:
        Arrays.equals(actual, expected)
    }

    def 'test dynamic array initialization should have equal parameters'() {
        given:
        def array = [Value.of('hello'), Value.of('world')]

        and:
        def value = new ArrayValue<>(ObjectClassValue.STRING, array)

        and:
        def expected = array*.value.toArray(String[]::new)

        when:
        def actual = value.value

        then:
        Arrays.equals(actual, expected)
    }

    def 'test toClass of array should return compatible array class'() {
        given:
        def value = new ArrayValue(ObjectClassValue.STRING, 3)

        when:
        def classValue = value.toClass()

        then:
        classValue.compatibleWith(value)
    }

    def 'test toString'() {
        given:
        def array = [Value.of('Hello'), Value.of(null), Value.of('world!')]

        and:
        def value = new ArrayValue(ObjectClassValue.STRING, array)

        when:
        def string = value.toString()

        then:
        string == "${ArrayValue.simpleName}(${ObjectClassValue.STRING}, ${array})"
    }

}
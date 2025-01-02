package it.fulminazzo.javaparser.executor.values.objects

import spock.lang.Specification

class ObjectValueTest extends Specification {

    def 'test toString of value #value should be equal to #expected'() {
        given:
        def objectValue = ObjectValue.of(value)
        expected = String.format(expected, value)

        when:
        def output = objectValue.toString()

        then:
        output == expected

        where:
        value                         | expected
        Byte.valueOf((byte) 1)        | 'ByteWrapperValue(%s)'
        Short.valueOf((short) 1)      | 'ShortWrapperValue(%s)'
        Character.valueOf((char) 'a') | 'CharacterValue(%s)'
        Integer.valueOf(1)            | 'IntegerValue(%s)'
        Long.valueOf(1)               | 'LongWrapperValue(%s)'
        Float.valueOf(1)              | 'FloatWrapperValue(%s)'
        Double.valueOf(1)             | 'DoubleWrapperValue(%s)'
        Boolean.valueOf(true)         | 'BooleanWrapperValue(%s)'
        Boolean.valueOf(false)        | 'BooleanWrapperValue(%s)'
        "Hello, world!"               | 'StringValue(%s)'
        [1, 2, 3]                     | 'ArrayListValue(%s)'
        ['key': 'value']              | 'LinkedHashMapValue(%s)'
        this                          | 'ObjectValue(%s)'
    }

}

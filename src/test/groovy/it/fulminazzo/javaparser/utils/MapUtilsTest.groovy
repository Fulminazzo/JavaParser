package it.fulminazzo.javaparser.utils

import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import spock.lang.Specification

class MapUtilsTest extends Specification {
    private Map map

    void setup() {
        this.map = [
                1: 'Hello',
                2: 'world',
        ]
    }

    def 'test getKeyAndValue of #key should return #expected'() {
        when:
        def actual = MapUtils.getKeyAndValue(map, key)

        then:
        actual == expected

        where:
        key | expected
        1   | new Tuple<>(1, 'Hello')
        2   | new Tuple<>(2, 'world')
    }

    def 'test getKeyAndValue of not present should throw exception'() {
        when:
        MapUtils.getKeyAndValue(this.map, 3)

        then:
        thrown(IllegalArgumentException)
    }

}

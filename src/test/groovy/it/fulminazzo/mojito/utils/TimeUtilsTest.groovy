package it.fulminazzo.mojito.utils

import spock.lang.Specification

class TimeUtilsTest extends Specification {

    def 'test #time formatTime should return #expected'() {
        when:
        def actual = TimeUtils.formatTime(time)

        then:
        actual == expected

        where:
        time    | expected
        3661000 | '1 hours, 1 minutes, 1 seconds'
        3660000 | '1 hours, 1 minutes'
        3601000 | '1 hours, 1 seconds'
        60000   | '1 minutes'
        1000    | '1 seconds'
        120     | '0.12 seconds'
        0       | '0 seconds'
    }

}

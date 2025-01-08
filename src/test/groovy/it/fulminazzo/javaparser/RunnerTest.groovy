package it.fulminazzo.javaparser

import spock.lang.Specification

class RunnerTest extends Specification {
    private Runner runner

    void setup() {
        this.runner = new MojitoRunner(this)
    }

    def 'test run(#parameters) should return #expected'() {
        when:
        def actual = this.runner.run(*parameters)

        then:
        if (expected == null) !actual.present
        else {
            actual.present
            actual.get() == expected
        }

        where:
        parameters                                                                                        | expected
        ['return 0;']                                                                                     | 0
        ['return variable', ['variable': 1]]                                                              | 1
        ['return variable', ['variable': null]]                                                           | null
        [new File('build/resources/test/runner_test.java')]                                               | 0
        [new File('build/resources/test/runner_test_variable.java'), ['variable': 1]]                     | 1
        [new File('build/resources/test/runner_test_variable.java'), ['variable': null]]                  | null
        [new File('build/resources/test/runner_test.java').newInputStream()]                              | 0
        [new File('build/resources/test/runner_test_variable.java').newInputStream(), ['variable': 1]]    | 1
        [new File('build/resources/test/runner_test_variable.java').newInputStream(), ['variable': null]] | null
    }

    def 'test runtime exception during execution'() {
        given:
        def expectedCause = new IllegalArgumentException('Hello, world!')
        def expected = RunnerException.of(expectedCause)

        when:
        this.runner.run('throw new IllegalArgumentException(\"Hello, world!\");')

        then:
        def e = thrown(RunnerException)
        e.message == expected.message
        def cause = e.cause
        cause instanceof IllegalArgumentException
        cause.message == expectedCause.message
    }

    def 'test file not found should throw wrapped exception'() {
        when:
        this.runner.run(new File('not_existing'))

        then:
        def e = thrown(RunnerException)
        e.message == RunnerException.cannotFindFile('not_existing').message
    }

    def 'test latestResult should be equal to result of run'() {
        when:
        def expected = this.runner.run('return 1;')

        then:
        this.runner.latestResult() == expected
    }

    def 'test ScopeException for JaCoCo coverage'() {
        given:
        def set = Mock(Set)
        set.iterator() >> ['one', 'two', 'one'].iterator()

        and:
        def map = Mock(Map)
        map.keySet() >> set

        when:
        this.runner.run('return 1;', map)

        then:
        noExceptionThrown()
    }

}

package it.fulminazzo.mojito.environment.scopetypes

import it.fulminazzo.mojito.environment.ScopeException
import spock.lang.Specification

import java.util.stream.Stream

class TryScopeTypeTest extends Specification {
    private TryScopeType type

    void setup() {
        this.type = new TryScopeType([
                IllegalArgumentException, IllegalStateException,
                IllegalAccessException, ScopeException,
        ].stream() as Stream<Class<Throwable>>)
    }

    def 'test name and toString should be equal'() {
        expect:
        this.type.toString() == this.type.name()
    }

    def 'test try scope type should be equal to #other'() {
        expect:
        this.type == other
        this.type.hashCode() == other.hashCode()

        where:
        other << [
                new TryScopeType([
                        IllegalArgumentException, IllegalStateException,
                        IllegalAccessException, ScopeException,
                ].stream() as Stream<Class<Throwable>>),
                new TryScopeType([
                        ScopeException, IllegalAccessException,
                        IllegalStateException, IllegalArgumentException,
                ].stream() as Stream<Class<Throwable>>),
                new TryScopeType([
                        ScopeException, IllegalStateException,
                        IllegalAccessException, IllegalArgumentException,
                ].stream() as Stream<Class<Throwable>>),
        ]
    }

    def 'test try scope type should not be equal to #other'() {
        expect:
        !this.type.equals(other)

        where:
        other << [
                null,
                BaseScopeType.values(),
                new TryScopeType([
                        IllegalArgumentException, IllegalStateException,
                        IllegalAccessException
                ].stream() as Stream<Class<Throwable>>),
        ].flatten()
    }

}

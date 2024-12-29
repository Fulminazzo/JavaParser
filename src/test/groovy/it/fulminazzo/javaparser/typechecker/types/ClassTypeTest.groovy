package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class ClassTypeTest extends Specification {

    def 'test of #className should return #expected'() {
        given:
        def type = ClassType.of(className)

        expect:
        type == expected

        where:
        className <<  [
                PrimitiveType.values().collect { it.name().toLowerCase() },
                ClassObjectType.values().collect { it.name() } .collect {
                     "${it[0]}${it.substring(1).toLowerCase()}"
                },
                Map.class.simpleName
        ].flatten()
        expected << [
                PrimitiveType.values(),
                ClassObjectType.values(),
                new Refl<>("${ClassObjectType.package.name}.CustomClassObjectType",
                        ObjectType.of('Map')).getObject()
        ].flatten()
    }

}

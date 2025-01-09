package it.fulminazzo.mojito.typechecker.types.arrays


import it.fulminazzo.mojito.typechecker.types.PrimitiveType
import it.fulminazzo.mojito.typechecker.types.TypeException
import spock.lang.Specification

class ArrayTypeTest extends Specification {
    private ArrayType array

    void setup() {
        this.array = new ArrayType(PrimitiveType.DOUBLE)
    }

    def 'test getField of "length" should return integer'() {
        when:
        def field = this.array.getField('length')

        then:
        field.variable == PrimitiveType.INT
    }

    def 'test getField should have normal behaviour in any other case'() {
        when:
        this.array.getField('not_existing')

        then:
        def e = thrown(TypeException)
        e.message == TypeException.fieldNotFound(this.array.toClass(), 'not_existing').message
    }

}

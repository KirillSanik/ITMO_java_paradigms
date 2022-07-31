package expression.generic;

public class GenericMultiply<T> extends GenericBinaryOperation<T> {

    protected GenericMultiply(GenericExpression<T> ex1, GenericExpression<T> ex2, OpGeneric<T> op) {
        super(ex1, ex2, op);
    }

    protected String getSign() {
        return " * ";
    }

    protected T calc(T a, T b) {
        return op.multiply(a, b);
    }
}

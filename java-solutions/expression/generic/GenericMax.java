package expression.generic;

public class GenericMax<T> extends GenericBinaryOperation<T> {

    protected GenericMax(GenericExpression<T> ex1, GenericExpression<T> ex2, OpGeneric<T> op) {
        super(ex1, ex2, op);
    }

    protected String getSign() {
        return " max ";
    }

    protected T calc(T a, T b) {
        return op.max(a, b);
    }
}

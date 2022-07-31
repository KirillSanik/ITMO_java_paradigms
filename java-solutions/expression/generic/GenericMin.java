package expression.generic;

public class GenericMin<T> extends GenericBinaryOperation<T> {

    protected GenericMin(GenericExpression<T> ex1, GenericExpression<T> ex2, OpGeneric<T> op) {
        super(ex1, ex2, op);
    }

    protected String getSign() {
        return " min ";
    }

    protected T calc(T a, T b) {
        return op.min(a, b);
    }
}

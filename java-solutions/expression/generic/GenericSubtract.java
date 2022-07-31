package expression.generic;

public class GenericSubtract<T> extends GenericBinaryOperation<T> {

    protected GenericSubtract(GenericExpression<T> ex1, GenericExpression<T> ex2, OpGeneric<T> op) {
        super(ex1, ex2, op);
    }

    protected String getSign() {
        return " - ";
    }

    protected T calc(T a, T b) {
        return op.subtract(a, b);
    }
}

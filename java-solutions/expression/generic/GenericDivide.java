package expression.generic;

public class GenericDivide<T> extends GenericBinaryOperation<T> {

    protected GenericDivide(GenericExpression<T> ex1, GenericExpression<T> ex2, OpGeneric<T> op) {
        super(ex1, ex2, op);
    }

    protected String getSign() {
        return " / ";
    }

    protected T calc(T a, T b) {
        return op.divide(a, b);
    }
}

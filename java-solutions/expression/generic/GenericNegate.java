package expression.generic;

public class GenericNegate<T> extends GenericUnarOperation<T> {

    public GenericNegate(GenericExpression<T> ex, OpGeneric<T> op) {
        super(ex, op);
    }

    @Override
    public String toString() {
        return "-(" + ex.toString() + ")";
    }

    public T calc(T num) {
        return op.negate(num);
    }
}

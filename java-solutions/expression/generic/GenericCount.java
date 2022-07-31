package expression.generic;

public class GenericCount<T> extends GenericUnarOperation<T> {

    public GenericCount(GenericExpression<T> ex, OpGeneric<T> op) {
        super(ex, op);
    }

    @Override
    public String toString() {
        return "count(" + ex.toString() + ")";
    }

    public T calc(T num) {
        return op.count(num);
    }
}

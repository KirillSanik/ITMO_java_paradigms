package expression.generic;

public class GenericPlus<T> extends GenericUnarOperation<T> {

    public GenericPlus(GenericExpression<T> ex, OpGeneric<T> op) {
        super(ex, op);
    }

    @Override
    public String toString() {
        return "(" + ex.toString() + ")";
    }

    public T calc(T num) {
        return num;
    }
}

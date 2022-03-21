package expression.generic;

public abstract class GenericUnarOperation<T> implements GenericExpression<T> {
    protected final GenericExpression<T> ex;
    protected final OpGeneric<T> op;

    public GenericUnarOperation(GenericExpression<T> ex, OpGeneric<T> op) {
        this.ex = ex;
        this.op = op;
    }

    public abstract String toString();

    protected abstract T calc(T num);

    public T evaluate(T x) {
        return calc(ex.evaluate(x));
    }

    public T evaluate(T x, T y, T z) {
        return calc(ex.evaluate(x, y, z));
    }
}

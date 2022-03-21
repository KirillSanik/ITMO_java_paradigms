package expression.generic;

public class GenericConst<T> implements GenericExpression<T> {
    private final T value;
    private final OpGeneric<T> op;

    public GenericConst(String value, OpGeneric<T> op) {
        this.value = op.parseNum(value);
        this.op = op;
    }

    @Override
    public T evaluate(T x) {
        return getValue();
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return getValue();
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return op.toString(value);
    }
}

package expression.generic;

public abstract class GenericBinaryOperation<T> implements GenericExpression<T> {

    protected final GenericExpression<T> ex1;
    protected final GenericExpression<T> ex2;
    protected final OpGeneric<T> op;

    protected GenericBinaryOperation(GenericExpression<T> ex1, GenericExpression<T> ex2, OpGeneric<T> op) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }

    public GenericExpression<T> getEx1() {
        return ex1;
    }

    public GenericExpression<T> getEx2() {
        return ex2;
    }

    @Override
    public T evaluate(T x) {
        return calc(ex1.evaluate(x), ex2.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return calc(ex1.evaluate(x, y, z), ex2.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + ex1.toString() + getSign()  + ex2.toString() + ")";
    }

    protected abstract T calc(T a, T b);

    protected abstract String getSign();
}

package expression.generic;

public class GenericOneExpression<T> implements GenericExpression<T> {

    private final GenericUnarOperation<T> UnarOp;

    public GenericOneExpression(GenericExpression<T> ex, char znak, OpGeneric<T> op) {
        switch (znak) {
            case '-' -> UnarOp = new GenericNegate<>(ex, op);
            case '+' -> UnarOp = new GenericPlus<>(ex, op);
            case 'c' -> UnarOp = new GenericCount<>(ex, op);
            //case 'l' -> UnarOp = new L0(ex, op);
            //case 't' -> UnarOp = new T0(ex, op);
            // :NOTE: you should throw here
            default -> UnarOp = new GenericPlus<>(ex, op);
        }
    }

    @Override
    public T evaluate(T x) {
        return UnarOp.evaluate(x);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return UnarOp.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return UnarOp.toString();
    }
}

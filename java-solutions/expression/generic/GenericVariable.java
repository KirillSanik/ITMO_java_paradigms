package expression.generic;

public class GenericVariable<T> implements GenericExpression<T> {

    private final String arg;

    public GenericVariable(String arg) {
        this.arg = arg;
    }

    @Override
    public T evaluate(T x) {
        return x;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return switch (arg) {
            case "x" -> evaluate(x);
            case "y" -> evaluate(y);
            default -> evaluate(z);
        };
    }

    @Override
    public String toString() {
        return getArg();
    }

    public String getArg() {
        return arg;
    }
}

package expression.generic;

public interface GenericExpression<T> {
    T evaluate(T x);

    T evaluate(T x, T y, T z);

    String toString();
}

package expression.generic;

public interface OpGeneric<T> {
    T parseNum(String num);

    T add(T arg1, T arg2);
    T subtract(T arg1, T arg2);

    T divide(T arg1, T arg2);
    T multiply(T arg1, T arg2);

    T min(T arg1, T arg2);
    T max(T arg1, T arg2);

    T negate(T arg);

    T count(T arg);

    String toString(T arg);
}

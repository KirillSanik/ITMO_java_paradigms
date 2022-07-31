package expression.generic;

public class OpChekedInteger extends OpInteger {

    @Override
    public Integer add(Integer arg1, Integer arg2) throws IllegalArgumentException {
        if (arg1 > 0 && arg2 > 0 && arg1 + arg2 < 0) {
            throw new IllegalArgumentException("Overflow");
        } else if (arg1 < 0 && arg2 < 0 && arg1 + arg2 >= 0) {
            throw new IllegalArgumentException("Overflow");
        }
        return arg1 + arg2;
    }

    @Override
    public Integer subtract(Integer arg1, Integer arg2) throws IllegalArgumentException {
        if (arg1 >= 0 && arg2 < 0 && arg1 - arg2 < 0) {
            throw new IllegalArgumentException("Overflow");
        } else if (arg1 < 0 && arg2 > 0 && arg1 - arg2 > 0) {
            throw new IllegalArgumentException("Overflow");
        }
        return arg1 - arg2;
    }

    @Override
    public Integer divide(Integer arg1, Integer arg2) throws IllegalArgumentException {
        if (arg2.equals(0)) {
            throw new IllegalArgumentException("Div by zero");
        }
        if (arg2 == -1 && arg1 == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Overflow");
        }
        return arg1 / arg2;
    }

    @Override
    public Integer multiply(Integer arg1, Integer arg2) throws IllegalArgumentException {
        if (arg1 == 0 || arg2 == 0) {
            return 0;
        }
        if (arg1 * arg2 / arg2 != arg1) {
            throw new IllegalArgumentException("Overflow");
        } else if (arg2 * arg1 / arg1 != arg2) {
            throw new IllegalArgumentException("Overflow");
        }
        return arg1 * arg2;
    }

    @Override
    public Integer negate(Integer arg) throws IllegalArgumentException {
        if (arg == -2147483648) {
            throw new IllegalArgumentException("Overflow");
        }
        return -arg;
    }
}

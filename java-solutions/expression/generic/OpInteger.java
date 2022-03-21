package expression.generic;

public class OpInteger implements OpGeneric<Integer>{
    @Override
    public Integer parseNum(String num) throws IllegalArgumentException {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("overflow");
        }
    }

    @Override
    public Integer add(Integer arg1, Integer arg2) {
        return arg1 + arg2;
    }

    @Override
    public Integer subtract(Integer arg1, Integer arg2) {
        return arg1 - arg2;
    }

    @Override
    public Integer divide(Integer arg1, Integer arg2) throws IllegalArgumentException {
        if (arg2.equals(0)) {
            throw new IllegalArgumentException("Div by zero");
        }
        return arg1 / arg2;
    }

    @Override
    public Integer multiply(Integer arg1, Integer arg2) {
        return arg1 * arg2;
    }

    @Override
    public Integer min(Integer arg1, Integer arg2) {
        return Math.min(arg1, arg2);
    }

    @Override
    public Integer max(Integer arg1, Integer arg2) {
        return Math.max(arg1, arg2);
    }

    @Override
    public Integer negate(Integer arg) {
        return -arg;
    }

    @Override
    public Integer count(Integer arg) {
        return Integer.bitCount(arg);
    }

    @Override
    public String toString(Integer arg) {
        return arg.toString();
    }
}

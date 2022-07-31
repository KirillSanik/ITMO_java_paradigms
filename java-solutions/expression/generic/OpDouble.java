package expression.generic;

public class OpDouble implements OpGeneric<Double>{
    @Override
    public Double parseNum(String num) throws IllegalArgumentException {
        try {
            return Double.parseDouble(num);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("overflow");
        }
    }

    @Override
    public Double add(Double arg1, Double arg2) {
        return arg1 + arg2;
    }

    @Override
    public Double subtract(Double arg1, Double arg2) {
        return arg1 - arg2;
    }

    @Override
    public Double divide(Double arg1, Double arg2) {
        return arg1 / arg2;
    }

    @Override
    public Double multiply(Double arg1, Double arg2) {
        return arg1 * arg2;
    }

    @Override
    public Double min(Double arg1, Double arg2) {
        return Math.min(arg1, arg2);
    }

    @Override
    public Double max(Double arg1, Double arg2) {
        return Math.max(arg1, arg2);
    }

    @Override
    public Double negate(Double arg) {
        return -arg;
    }

    @Override
    public Double count(Double arg) {
        return (double) (Long.bitCount(Double.doubleToLongBits(arg)));
    }

    @Override
    public String toString(Double arg) {
        return arg.toString();
    }
}

package expression.generic;

import java.math.BigInteger;

public class OpBigInt implements OpGeneric<BigInteger> {

    @Override
    public BigInteger parseNum(String num) throws IllegalArgumentException {
        try {
            return new BigInteger(num);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("number format ex");
        }
    }

    @Override
    public BigInteger add(BigInteger arg1, BigInteger arg2) {
        return arg1.add(arg2);
    }

    @Override
    public BigInteger subtract(BigInteger arg1, BigInteger arg2) {
        return arg1.subtract(arg2);
    }

    @Override
    public BigInteger divide(BigInteger arg1, BigInteger arg2) throws IllegalArgumentException {
        if (arg2.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Div by zero");
        }
        return arg1.divide(arg2);
    }

    @Override
    public BigInteger multiply(BigInteger arg1, BigInteger arg2) {
        return arg1.multiply(arg2);
    }

    @Override
    public BigInteger min(BigInteger arg1, BigInteger arg2) {
        return arg1.min(arg2);
    }

    @Override
    public BigInteger max(BigInteger arg1, BigInteger arg2) {
        return arg1.max(arg2);
    }

    @Override
    public BigInteger negate(BigInteger arg) {
        return arg.negate();
    }

    @Override
    public BigInteger count(BigInteger arg) {
        return BigInteger.valueOf(arg.bitCount());
    }

    @Override
    public String toString(BigInteger arg) {
        return arg.toString();
    }
}

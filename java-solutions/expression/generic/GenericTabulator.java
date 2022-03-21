package expression.generic;

public class GenericTabulator  implements Tabulator{
    private <T> Object[][][] calculateResult(OpGeneric <T> op, String mode, String expression,
                                             int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        GenericParser<T> parser = new GenericParser<>(mode, op);
        GenericExpression<T> resultEx;
        resultEx = parser.parse(expression);

        Object[][][] result = new Object[x2 - x1 + 1][ y2 - y1 + 1][z2 - z1 + 1];

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = resultEx.evaluate(
                                op.parseNum(Integer.toString(i)),
                                op.parseNum(Integer.toString(j)),
                                op.parseNum(Integer.toString(k)));
                    } catch (IllegalArgumentException e) {
                        result[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        OpGeneric<?> op;
        switch (mode) {
            case "i" -> op = new OpChekedInteger();
            case "d" -> op = new OpDouble();
            case "bi" -> op = new OpBigInt();
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
        return calculateResult(op, mode, expression, x1, x2, y1, y2, z1, z2);
    }
}
